package org.oslo.console.cli.base;

import org.oslo.common.cli.CommandLineInterpreter;
import org.oslo.server.prevayler.persistance.PrevaylerPersister;
import org.oslo.server.prevayler.system.RantSystem;
import org.prevayler.Prevayler;
import org.oslo.common.datamodel.process.Process;
import org.oslo.common.datamodel.group.MetricGroup;

import java.util.StringTokenizer;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jul 1, 2003
 * Time: 9:13:02 PM
 * To change this template use Options | File Templates.
 */
public class BaseConsoleCommands implements CommandLineInterpreter {

    public String[] getCommands() {
        String commands[] = new String[3];
        commands[0] = "LISTPROCESSES";
        commands[1] = "LISTPROCESS";
        commands[2] = "QUIT";
        return commands;
    }

    public String[] getCommandHelp() {
        String helpStrings[] = new String[3];
        helpStrings[0] = "LISTPROCESSES \n\t Lists all available process recorded";
        helpStrings[1] = "LISTPROCESS <processID> \n\t Lists all metrics attached to this process ID";
        helpStrings[2] = "QUIT \n\t Quits the application";
        return helpStrings;
    }

    public String execute(String command) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(command, " ");

        // Get command
        String commandToken = tokenizer.nextToken();

        if ("LISTPROCESSES".equals(commandToken.toUpperCase())) {
            return executeListProcesses(tokenizer);
        } else if ("LISTPROCESS".equals(commandToken.toUpperCase())) {
            return executeListProcess(tokenizer);
        } else {
            return null;
        }
    }

    private String executeListProcess(StringTokenizer tokenizer) {
        String processId = tokenizer.nextToken();

        PrevaylerPersister prevaylerPersister = PrevaylerPersister.getInstance();
        Prevayler prevayler = prevaylerPersister.getPrevayler();
        RantSystem rantSystem = (RantSystem) prevayler.prevalentSystem();

        // Get all the available processes
        Process process = rantSystem.getProcess(processId);

        // Check if the process is there
        if(process == null)
            return "Could not find the process with processId = " + processId;

        // Ok get all the metric groups and present them
        Iterator iterator = process.getMetricGroups().values().iterator();
        String metricString = " ";

        while (iterator.hasNext()) {
            MetricGroup metricGroup = (MetricGroup) iterator.next();
            metricString += "\t" + metricGroup.getPluginName() + "\n";
        }

        return metricString;
    }

    private String executeListProcesses(StringTokenizer tokenizer) {
        PrevaylerPersister prevaylerPersister = PrevaylerPersister.getInstance();
        Prevayler prevayler = prevaylerPersister.getPrevayler();
        RantSystem rantSystem = (RantSystem) prevayler.prevalentSystem();

        String processesString = " ";

        // Get all the available processes
        Iterator processes = rantSystem.getProcesses();

        while (processes.hasNext()) {
            Process process = (Process) processes.next();
            processesString = "\t" + process.getProcessID() + "\n";
        }

        return processesString.substring(0, processesString.length() - 1);
    }
}
