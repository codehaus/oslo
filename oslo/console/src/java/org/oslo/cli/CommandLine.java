package org.oslo.cli;

import org.prevayler.Prevayler;
import org.oslo.common.cli.CommandLineInterpreter;
import org.oslo.rantserver.prevayler.persistance.PrevaylerPersister;
import org.oslo.rantserver.prevayler.system.RantSystem;
import org.oslo.plugins.sequenceplugin.cli.SequenceConsoleCommands;
import org.oslo.plugins.performanceplugin.cli.PeformanceConsoleCommands;
import org.oslo.cli.base.BaseConsoleCommands;

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
        addCommands(new SequenceConsoleCommands());
        addCommands(new PeformanceConsoleCommands());
        addCommands(new BaseConsoleCommands());
        interpreters.add(new SequenceConsoleCommands());
        interpreters.add(new PeformanceConsoleCommands());
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

            System.exit(1);
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
}