package org.oslo.plugins.sequenceplugin.cli;

import org.oslo.common.cli.CommandLineInterpreter;
import org.oslo.common.datamodel.process.Process;
import org.oslo.common.datamodel.group.MetricGroup;
import org.oslo.common.datamodel.metric.Metric;
import org.oslo.common.prevayler.system.RantSystem;
import org.oslo.common.prevayler.persistance.PrevaylerPersister;
import org.oslo.plugins.sequenceplugin.metric.SequenceMetric;
import org.oslo.plugins.sequenceplugin.plugin.SequencePlugin;
import org.prevayler.Prevayler;

import java.util.*;
import java.io.File;
import java.security.InvalidParameterException;
import java.awt.image.BufferedImage;
import java.awt.*;

import com.zanthan.sequence.diagram.Diagram;
import com.zanthan.sequence.diagram.NodeFactoryImpl;
import com.zanthan.sequence.parser.SimpleParserImpl;
import com.zanthan.sequence.swing.display.SwingStringMeasure;
import com.zanthan.sequence.swing.display.SwingPainter;
import com.zanthan.sequence.layout.LayoutData;

import javax.imageio.ImageIO;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jul 25, 2003
 * Time: 2:05:36 PM
 * To change this template use Options | File Templates.
 */
public class SequenceConsoleCommands implements CommandLineInterpreter {
    /**
     * Command line interface
     */
    public String[] getCommandHelp() {
        String helpStrings[] = new String[3];
        helpStrings[0] = "GENERATESEQUENCEDIAGRAM <processID> <startingClass> <startingMentho> \n\t Generates a sequenceplugin diagram from the specified starting point";
        helpStrings[1] = "FINDSEQUENCESTARTMETHODS <processID> \n\t Finds all methods that are enterypoints to a recorded sequenceplugin";
        helpStrings[2] = "GENERATEALLSEQUENCEDIAGRAMS <processID> <Output Directory>\n\t Generates sequenceplugin jpegs of all available entry points in the given process";
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

        // Starting sequenceplugin
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

        // Ok we have a startMetric, generate the actual sequenceplugin diagram
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
            MetricGroup metricGroup = (MetricGroup) process.getMetricGroup(SequencePlugin.class.getName());

            if (metricGroup != null) {
                // Ok now create a list of metrics that do not have a caller
                HashMap metrics = metricGroup.getMetrics();
                Iterator iterator = metrics.values().iterator();

                while (iterator.hasNext()) {
                    SequenceMetric sequenceMetric = (SequenceMetric) iterator.next();

                    // Establish if this sequenceplugin metric has any parents
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
        MetricGroup metricgroup = (MetricGroup) process.getMetricGroup(SequencePlugin.class.getName());

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
