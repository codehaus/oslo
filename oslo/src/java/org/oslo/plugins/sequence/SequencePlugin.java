package org.oslo.plugins.sequence;

import org.oslo.common.plugin.Plugin;
import org.oslo.common.plugin.PluginImpl;
import org.oslo.common.datamodel.metric.Metric;
import org.oslo.rantserver.prevayler.persistance.PrevaylerPersister;
import org.oslo.rantserver.prevayler.system.RantSystem;
import org.oslo.metrics.sequence.SequenceMetric;
import org.oslo.common.cli.CommandLineInterpreter;
import org.prevayler.Prevayler;
import org.oslo.common.datamodel.process.Process;
import org.oslo.common.datamodel.group.MetricGroup;

import java.util.*;
import java.security.InvalidParameterException;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import com.zanthan.sequence.swing.display.SwingPainter;
import com.zanthan.sequence.swing.display.SwingStringMeasure;
import com.zanthan.sequence.layout.LayoutData;
import com.zanthan.sequence.diagram.Diagram;
import com.zanthan.sequence.diagram.NodeFactoryImpl;
import com.zanthan.sequence.parser.SimpleParserImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 14, 2003
 * Time: 11:25:25 PM
 * To change this template use Options | File Templates.
 */
public class SequencePlugin extends PluginImpl implements Plugin, CommandLineInterpreter {
    private static final String IDENTIFIER = "Sequence";
    private String processId;
    private static Log log = LogFactory.getLog(SequencePlugin.class);

    public String getIdentifier() {
        return IDENTIFIER;
    }

    /**
     *
     * @param metricString
     * @return
     * @throws Exception
     *
     * The dataformat is of the following type [Sequence] className methodName callerClass callerMethod [/Sequence]
     */
    public Metric parseMetricString(String metricString) throws Exception {
        // Ok parse the metric
        // Its on the form [IDENTIFIER] Data Data Data Data .... [/IDENTIFIER]
        String processId = getProcessId(metricString);
        String dataString = getDataString(metricString);

        StringTokenizer tokenizer = new StringTokenizer(dataString, " ");

        // Ok get all the data we need, we know how many elements there are
        String callerClass = tokenizer.nextToken();
        String callerMethod = tokenizer.nextToken();
        String calleeClass = tokenizer.nextToken();
        String calleeMethod = tokenizer.nextToken();
        String returnType = tokenizer.nextToken();

        ArrayList parameters = new ArrayList();

        while (tokenizer.hasMoreTokens()) {
            parameters.add(tokenizer.nextToken());
        }

        // Ok we need to construct a Metric object
        SequenceMetric sequenceMetric = new SequenceMetric(Long.toString(System.currentTimeMillis()), callerClass, callerMethod, calleeClass, calleeMethod, returnType, parameters);
        // Add the process Id number so that we can have a link between all objects for a session
        sequenceMetric.setProcessId(processId);
        return sequenceMetric;
    }

    public String createMetricString(Metric metric) throws Exception {
        SequenceMetric sequenceMetric = (SequenceMetric) metric;
        String sequenceString = sequenceMetric.getProcessId() + " [Sequence] " + sequenceMetric.getCallerClass() + " "
                + sequenceMetric.getCallerMethod() + " "
                + sequenceMetric.getCalleeClass() + " "
                + sequenceMetric.getCalleeMethod() + " "
                + sequenceMetric.getReturnType();

        ArrayList parameters = sequenceMetric.getParameters();
        Iterator paramIt = parameters.iterator();

        while (paramIt.hasNext()) {
            String parameter = (String) paramIt.next();
            sequenceString += " " + parameter;
        }

        sequenceString += "[/Sequence]";
        log.debug("Created sequenceString: " + sequenceString);

        return sequenceString;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    /**
     * Command line interface
     */
    public String[] getCommandHelp() {
        String helpStrings[] = new String[3];
        helpStrings[0] = "GENERATESEQUENCEDIAGRAM <processID> <startingClass> <startingMentho> \n\t Generates a sequence diagram from the specified starting point";
        helpStrings[1] = "FINDSEQUENCESTARTMETHODS <processID> \n\t Finds all methods that are enterypoints to a recorded sequence";
        helpStrings[2] = "GENERATEALLSEQUENCEDIAGRAMS <processID> <Output Directory>\n\t Generates sequence jpegs of all available entry points in the given process";
        return helpStrings;
    }

    public String[] getCommands() {
        String commands[] = new String[3];
        commands[0] = "GENERATESEQUENCEDIAGRAM";
        commands[1] = "FINDSEQUENCESTARTMETHODS";
        commands[2] = "GENERATEALLSEQUENCEDIAGRAMS";
        return commands;
    }

    public String execute(String command) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(command, " ");

        // Get command
        String commandToken = tokenizer.nextToken();

        if ("GENERATESEQUENCEDIAGRAM".equals(commandToken.toUpperCase())) {
            return executeGenerateSequenceDiagram(tokenizer);
        } else if ("FINDSEQUENCESTARTMETHODS".equals(commandToken.toUpperCase())) {
            return executeFindSequenceStartMethods(tokenizer);
        } else if ("GENERATEALLSEQUENCEDIAGRAMS".equals(commandToken.toUpperCase())) {
            return executeGenerateAllSequenceDiagrams(tokenizer);
        } else {
            return null;
        }
    }

    private String executeGenerateAllSequenceDiagrams(StringTokenizer command) throws Exception {
        // Ok get the commands expected
        String processId = command.nextToken();
        String outputdirectory = command.nextToken();
        StringBuffer sequenceInformation = new StringBuffer();

        // Ok check if the output directory exits
        File directory = new File(outputdirectory);

        // Check if the directory exists and if it is in fact a directory
        if (!directory.exists() || !directory.isDirectory())
            throw new Exception("Directory specified does not exist");

        // Ok, now get all the starting points
        ArrayList entryPoints = findEntryPoints((RantSystem) PrevaylerPersister.getInstance().getPrevayler().prevalentSystem(), processId);
        Iterator entryIterator = entryPoints.iterator();

        ArrayList sequences = generateSequenceStructure(processId);

        // Iterate over the collected metrics
        while (entryIterator.hasNext()) {
            SequenceMetric sequenceMetric = (SequenceMetric) entryIterator.next();

            // Ok for each metric generate a graphics file in the specified catalog
            String sequenceString = this.generateSequenceString(sequenceMetric, sequences);

            sequenceInformation.append("\t Generating PNG for entry Point: " + sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod() + "\n");

            // Ok generate the file
            this.generateSequenceDiagramPNG(outputdirectory + "/" + processId + "-" + sequenceMetric.getCallerClass() + "-" + sequenceMetric.getCallerMethod() + ".png", sequenceString);
        }

        return sequenceInformation.toString();
    }

    public String executeGenerateSequenceDiagram(StringTokenizer command) throws Exception {
        // Get all parameters
        String processID = command.nextToken();
        String startingClass = command.nextToken();
        String startingMethod = command.nextToken();

        // Ok fetch list of metrics
        ArrayList sequences = generateSequenceStructure(processID);
        Iterator seqIterator = sequences.iterator();

        // Starting sequence
        SequenceMetric startSequenceMetric = null;

        // Ok check to see if we can find the class
        while (seqIterator.hasNext()) {
            SequenceMetric seqMetric = (SequenceMetric) seqIterator.next();

            if (seqMetric.getCallerClass().equals(startingClass) && seqMetric.getCallerMethod().equals(startingMethod)) {
                startSequenceMetric = seqMetric;
                break;
            }
        }

        // If there is a startMetric, then show it
        if (startSequenceMetric == null)
            return "The startClass and startMethod did not exist";

        // Ok we have a startMetric, generate the actual sequence diagram
        return generateSequenceString(startSequenceMetric, sequences);
    }

    public String executeFindSequenceStartMethods(StringTokenizer command) throws Exception {
        PrevaylerPersister prevaylerPersister = PrevaylerPersister.getInstance();
        Prevayler prevayler = prevaylerPersister.getPrevayler();
        RantSystem rantSystem = (RantSystem) prevayler.prevalentSystem();

        // Get the process id number
        String processId = command.nextToken();

        ArrayList topPoints = findEntryPoints(rantSystem, processId);

        Iterator topPointsIterator = topPoints.iterator();
        String metricsString = "Found the following application entry points\n";

        while (topPointsIterator.hasNext()) {
            SequenceMetric sequenceMetric = (SequenceMetric) topPointsIterator.next();
            metricsString += "\t" + sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod() + "\n";
        }

        return metricsString;
    }

    /**
     * Finds the entry points for a process, example might be the main method or
     * a method on a EJB bean
     * @param rantSystem
     * @param processId
     * @return
     */
    private ArrayList findEntryPoints(RantSystem rantSystem, String processId) {
        ArrayList topPoints = new ArrayList();

        if (rantSystem.getProcess(processId) != null) {
            Process process = rantSystem.getProcess(processId);

            // Ok got the process, now check for processes that are not called by anyone
            MetricGroup metricGroup = (MetricGroup) process.getMetricGroup(this.getClass().getName());

            if (metricGroup != null) {
                // Ok now create a list of metrics that do not have a caller
                HashMap metrics = metricGroup.getMetrics();
                Iterator iterator = metrics.values().iterator();

                while (iterator.hasNext()) {
                    SequenceMetric sequenceMetric = (SequenceMetric) iterator.next();

                    // Establish if this sequence metric has any parents
                    Iterator allSequenceMetrics = metrics.values().iterator();
                    boolean hasParent = false;

                    while (allSequenceMetrics.hasNext()) {
                        SequenceMetric sequenceMetricElement = (SequenceMetric) allSequenceMetrics.next();

                        // Ok, if this has a parent, signal and break the loop
                        if (sequenceMetric.getCallerClass().equals(sequenceMetricElement.getCalleeClass()) && sequenceMetric.getCallerMethod().equals(sequenceMetricElement.getCalleeMethod())) {
                            hasParent = true;
                            break;
                        }
                    }

                    if (hasParent == false)
                        topPoints.add(sequenceMetric);
                }
            }
        }

        return topPoints;
    }

    private ArrayList generateSequenceStructure(String processId) throws Exception {
        PrevaylerPersister prevaylerPersister = PrevaylerPersister.getInstance();
        Prevayler prevayler = prevaylerPersister.getPrevayler();
        RantSystem rantSystem = (RantSystem) prevayler.prevalentSystem();

        // Get a specific process
        Process process = (Process) rantSystem.getProcess(processId);

        if (process == null)
            throw new InvalidParameterException("No such process exists");

        ArrayList allNodes = new ArrayList();

        // Get the metric group for this plugin
        MetricGroup metricgroup = (MetricGroup) process.getMetricGroup(this.getClass().getName());

        if (metricgroup == null)
            throw new InvalidParameterException("No metric groups found");

        Iterator metrics = metricgroup.getMetrics().values().iterator();

        while (metrics.hasNext()) {
            Metric metric = (Metric) metrics.next();

            // Ok write the metric out
            SequenceMetric sequenceMetric = (SequenceMetric) metric;
            allNodes.add(sequenceMetric);
        }

        return allNodes;
    }

    private String generateSequenceString(SequenceMetric sequenceMetric, ArrayList allNodes) {
        // We need to ensure that all the the top level connections are captured
        String sequenceString = "(" + sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod();
        Iterator nodesIterator = allNodes.iterator();

        while (nodesIterator.hasNext()) {
            SequenceMetric choosenSequenceMetric = (SequenceMetric) nodesIterator.next();

            if (choosenSequenceMetric.getCallerClass().equals(sequenceMetric.getCallerClass()) && choosenSequenceMetric.getCallerMethod().equals(sequenceMetric.getCallerMethod())) {
                sequenceString += traverseAllNodes(choosenSequenceMetric, allNodes, "\t");
            }
        }

        return sequenceString + "\n)";
    }

    private String traverseAllNodes(SequenceMetric sequenceMetric, ArrayList allNodes, String tabs) {
        Iterator nodesIterator = allNodes.iterator();
        String returnString = "";

        while (nodesIterator.hasNext()) {
            SequenceMetric choosenSequenceMetric = (SequenceMetric) nodesIterator.next();

            if (choosenSequenceMetric.getCallerClass().equals(sequenceMetric.getCalleeClass()) && choosenSequenceMetric.getCallerMethod().equals(sequenceMetric.getCalleeMethod())) {
                returnString += traverseAllNodes(choosenSequenceMetric, allNodes, tabs + "\t");
            }
        }

        String addString = returnString;
        String classString = "\n" + tabs + "(" + sequenceMetric.getCalleeClass() + " " + sequenceMetric.getCalleeMethod() + "<";

        ArrayList parameters = sequenceMetric.getParameters();
        Iterator paramIterator = parameters.iterator();

        while (paramIterator.hasNext()) {
            String parameter = (String) paramIterator.next();
            classString += parameter + ",";
        }

        // Adjust for the case without parameters
        if (parameters.size() ==
                0)
            classString += " ";

        return classString.substring(0, classString.length() - 1) + "> " + "\"" + sequenceMetric.getReturnType() + "\" " + addString + ")";
    }

    private void generateSequenceDiagramPNG(String outputFile, String sequenceString) throws Exception {
        // Create the diagram
        Diagram diagram = new Diagram(new SimpleParserImpl(), new NodeFactoryImpl());
        diagram.parse(sequenceString);

        // Layout the diagram
        BufferedImage bi = new BufferedImage(10, 10, 2);
        Graphics2D graphics = bi.createGraphics();
        com.zanthan.sequence.layout.StringMeasure sm = new SwingStringMeasure(graphics);
        LayoutData layoutData = new LayoutData(sm);
        diagram.layout(layoutData);

        // Write the png file to disk
        int height = layoutData.getHeight();
        int width = layoutData.getWidth();
        BufferedImage png = new BufferedImage(width, height, 2);
        Graphics2D pngGraphics = png.createGraphics();
        pngGraphics.setClip(0, 0, width, height);
        Map hintsMap = new HashMap();

        hintsMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        pngGraphics.addRenderingHints(hintsMap);
        pngGraphics.setBackground(Color.white);
        pngGraphics.setColor(Color.white);
        pngGraphics.fillRect(0, 0, width, height);
        SwingPainter painter = new SwingPainter();
        painter.setGraphics(pngGraphics);
        layoutData.paint(painter);
        ImageIO.write(png, "png", new File(outputFile));
    }
}
