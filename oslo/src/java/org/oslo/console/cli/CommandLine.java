package org.oslo.console.cli;

import org.oslo.server.prevayler.persistance.PrevaylerPersister;
import org.oslo.server.prevayler.system.RantSystem;
import org.prevayler.Prevayler;
import org.oslo.server.prevayler.datamodel.process.Process;
import org.oslo.server.prevayler.datamodel.group.MetricGroup;
import org.oslo.server.prevayler.datamodel.metric.Metric;
import org.oslo.metrics.sequence.SequenceMetric;
import org.oslo.plugins.sequence.SequencePlugin;
import org.oslo.console.cli.base.BaseConsoleCommands;

import java.util.*;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 18, 2003
 * Time: 6:37:42 PM
 * To change this template use Options | File Templates.
 */
public class CommandLine {
    private Hashtable commandLinePluginCommands = new Hashtable();
    private ArrayList interpreters = new ArrayList();

    public static void main(String[] args) {
        new CommandLine().executeConsole();
    }

    private void intialize() {
        System.out.println("-- INITIALIZING SYSTEM --------------------------------------------------------");
        PrevaylerPersister prevaylerPersister = PrevaylerPersister.getInstance();
        Prevayler prevayler = prevaylerPersister.getPrevayler();
        RantSystem rantSystem = (RantSystem) prevayler.prevalentSystem();
        System.out.println("-- FINISHED INITIALIZING SYSTEM -----------------------------------------------");
    }

    public void executeConsole() {
        // Initialize prevayler
        intialize();

        // Add all the commands
        addCommands(new SequencePlugin());
        addCommands(new BaseConsoleCommands());
        interpreters.add(new SequencePlugin());
        interpreters.add(new BaseConsoleCommands());

        try {
            // Ok now we need to execute and read
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // Read a line
            System.out.print("COMMAND:> ");
            String inputLine = reader.readLine();

            // Else go into interactive modus
            while (!"QUIT".equals(inputLine.toUpperCase())) {
                int index = inputLine.indexOf(" ");
                String equalString = null;

                // Clean away empty spaces of the command if necessary
                if (index != -1)
                    equalString = inputLine.substring(0, index);
                else
                    equalString = inputLine;

                // Ok find the command, ripping away the rest
                // Fetch the interpreter
                CommandLineInterpreter interpreter = (CommandLineInterpreter) commandLinePluginCommands.get(equalString.toUpperCase());

                // Check if the interpreter exists
                if (interpreter != null) {


                    String message = null;
                    try {
                        message = interpreter.execute(inputLine);
                        System.out.println(message);
                    } catch (Exception e) {
                        System.out.println("Illegal command, type HELP for available commands");
                        e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                    }

                    // Write the information to the stringbuffer (for later writing to the log file)
                    //writeBuffer.append("COMMAND:> " + inputLine + "\n" + message + "\n");
                    // Write the result to the console
                } else {
                    // Check if this is the help command
                    executeBaseSystemCommands(inputLine, index);
                }

                // Read the next command
                System.out.print("COMMAND:> ");
                inputLine = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    private void executeBaseSystemCommands(String inputLine, int index) {
        if ("HELP".equals(inputLine.toUpperCase())) {
            // Ok need to call plugins to show their help
            Iterator it = interpreters.iterator();

            while (it.hasNext()) {
                CommandLineInterpreter commandLineInterpreter = (CommandLineInterpreter) it.next();
                String[] helpStrings = commandLineInterpreter.getCommandHelp();

                for (int i = 0; i < helpStrings.length; i++) {
                    String helpString = helpStrings[i];
                    System.out.println("HELP:> " + helpString);
                }
            }
        } else {
            System.out.println("ERROR: Illegal command, Type HELP to get an overview of commands");
        }
    }

    private void addCommands(CommandLineInterpreter interpreter) {
        String[] commands = interpreter.getCommands();

        for (int cmdIndex = 0; cmdIndex < commands.length; cmdIndex++) {
            String command = commands[cmdIndex];
            commandLinePluginCommands.put(command, interpreter);
        }
    }

    /*public void ShowAllProcessesFull() {
        BufferedOutputStream bufferedOutputStream = null;
        Hashtable hashtable = new Hashtable();
        SequenceMetric startmetric = null;

        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("sequence_server.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }

        PrevaylerPersister prevaylerPersister = PrevaylerPersister.getInstance();
        Prevayler prevayler = prevaylerPersister.getPrevayler();
        RantSystem rantSystem = (RantSystem) prevayler.prevalentSystem();

        Iterator processes = rantSystem.getProcesses();
        //Collection metrics = null;
        ArrayList allNodes = new ArrayList();

        while (processes.hasNext()) {
            Process process = (Process) processes.next();

            // Ok write process info
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("Process : " + process.getProcessID());

            // Write metric groups out
            Iterator metricgroups = process.getMetricGroups().values().iterator();
            //metrics = process.getMetricGroups().values();

            while (metricgroups.hasNext()) {
                MetricGroup metricgroup = (MetricGroup) metricgroups.next();

                // Write metric group info
                System.out.println("Metricgroup : " + metricgroup.getPluginName() + " description " + metricgroup.getDescription());

                Iterator metrics = metricgroup.getMetrics().values().iterator();
                boolean first = true;

                while (metrics.hasNext()) {
                    Metric metric = (Metric) metrics.next();

                    // Ok write the metric out
                    if (metric instanceof SequenceMetric) {
                        SequenceMetric sequenceMetric = (SequenceMetric) metric;
                        System.out.println("SequenceMetric : " + sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod() + " " + sequenceMetric.getCalleeClass() + " " + sequenceMetric.getCalleeMethod());

                        // Find start method
                        if (sequenceMetric.getCallerMethod().equals("main"))
                            startmetric = sequenceMetric;

                        allNodes.add(sequenceMetric);

                        if (hashtable.containsKey(sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod())) {
                            ((Hashtable) hashtable.get(sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod())).put(sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod(), sequenceMetric);
                        } else {
                            Hashtable newSequenceHashtable = new Hashtable();
                            newSequenceHashtable.put(sequenceMetric.getCalleeClass() + " " + sequenceMetric.getCalleeMethod(), sequenceMetric);
                            hashtable.put(sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod(), newSequenceHashtable);
                        }
                    }
                }
            }

            System.out.println("------------------------------------------------------------------------------------------");
        }

        String finalString = generateSequenceString(startmetric, allNodes);
        System.out.println(finalString);
    } */

/*    public String generateSequenceString(SequenceMetric sequenceMetric, ArrayList allNodes) {
        return "(" + sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod() + traverseAllNodes(sequenceMetric, allNodes, "\t");
    }

    public String traverseAllNodes(SequenceMetric sequenceMetric, ArrayList allNodes, String tabs) {
        Iterator nodesIterator = allNodes.iterator();
        String returnString = "";

        while (nodesIterator.hasNext()) {
            SequenceMetric choosenSequenceMetric = (SequenceMetric) nodesIterator.next();

            if (choosenSequenceMetric.getCallerClass().equals(sequenceMetric.getCalleeClass()) && choosenSequenceMetric.getCallerMethod().equals(sequenceMetric.getCalleeMethod())) {
                returnString += traverseAllNodes(choosenSequenceMetric, allNodes, tabs + "\t");
            }
        }

        String addString = returnString;*/

    /*if("".equals(returnString))
        addString = ")\n";
    else
        addString = tabs + returnString + "\n)";*/

    //return sequenceMetric.getCallerClass() + " " + sequenceMetric.getCallerMethod() + " " + sequenceMetric.getCalleeClass() + " " + sequenceMetric.getCalleeMethod() + "\n" + returnString;
//        return "\n" + tabs + "(" + sequenceMetric.getCalleeClass() + " " + sequenceMetric.getCalleeMethod() + addString + ")";
//   }
}