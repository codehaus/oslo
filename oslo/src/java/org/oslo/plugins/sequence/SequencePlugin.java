package org.oslo.plugins.sequence;

import org.oslo.server.plugin.Plugin;
import org.oslo.server.prevayler.datamodel.metric.Metric;
import org.oslo.server.prevayler.persistance.PrevaylerPersister;
import org.oslo.server.prevayler.system.RantSystem;
import org.oslo.metrics.sequence.SequenceMetric;
import org.oslo.console.cli.CommandLineInterpreter;
import org.prevayler.Prevayler;
import org.oslo.server.prevayler.datamodel.process.Process;
import org.oslo.server.prevayler.datamodel.group.MetricGroup;

import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ArrayList;
import java.security.InvalidParameterException;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 14, 2003
 * Time: 11:25:25 PM
 * To change this template use Options | File Templates.
 */
public class SequencePlugin implements Plugin, CommandLineInterpreter {
    private static final String IDENTIFIER = "Sequence";
    private String processId;

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

    public String getProcessId(String metricString) {
        int processIdPosition = metricString.indexOf(" ");
        return metricString.substring(0, processIdPosition);
    }

    public String getDataString(String metricString) {
        // Ok remove the tags from the front and back from the data
        int sequenceStartPosition = metricString.indexOf("[" + IDENTIFIER + "]");
        int sequenceEndPosition = metricString.indexOf("[/" + IDENTIFIER + "]");

        return metricString.substring(sequenceStartPosition + IDENTIFIER.length() + 2 + 1, sequenceEndPosition);
    }

    public String createMetricString(Metric metric) throws Exception {
        SequenceMetric sequenceMetric = (SequenceMetric) metric;
        String sequenceString = sequenceMetric.getProcessId() + " [Sequence] " + sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod() + " " + sequenceMetric.getCalleeClass() + " " + sequenceMetric.getCalleeMethod() + " " + sequenceMetric.getReturnType();

        ArrayList parameters = sequenceMetric.getParameters();
        Iterator paramIt = parameters.iterator();

        while (paramIt.hasNext()) {
            String parameter = (String) paramIt.next();
            sequenceString += " " + parameter;
        }

        sequenceString += "[/Sequence]";
        System.out.println(sequenceString);

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
        String helpStrings[] = new String[2];
        helpStrings[0] = "GENERATESEQUENCEDIAGRAM <processID> <startingClass> <startingMentho> \n\t Generates a sequence diagram from the specified starting point";
        helpStrings[1] = "FINDSEQUENCESTARTMETHODS <processID> \n\t Finds all methods that are enterypoints to a recorded sequence";
        return helpStrings;
    }

    public String[] getCommands() {
        String commands[] = new String[2];
        commands[0] = "GENERATESEQUENCEDIAGRAM";
        commands[1] = "FINDSEQUENCESTARTMETHODS";
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
        } else {
            return null;
        }
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
        return null;
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

        // Write metric group info
//        System.out.println("Metricgroup : " + metricgroup.getPluginName() + " description " + metricgroup.getDescription());

        Iterator metrics = metricgroup.getMetrics().values().iterator();
        boolean first = true;

        while (metrics.hasNext()) {
            Metric metric = (Metric) metrics.next();

            // Ok write the metric out
            SequenceMetric sequenceMetric = (SequenceMetric) metric;
//            System.out.println("SequenceMetric : " + sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod() + " " + sequenceMetric.getCalleeClass() + " " + sequenceMetric.getCalleeMethod());
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
        if(parameters.size() == 0)
            classString += " ";

        return classString.substring(0, classString.length() - 1) + "> " + "\"" + sequenceMetric.getReturnType() + "\" " + addString + ")";
    }
}
