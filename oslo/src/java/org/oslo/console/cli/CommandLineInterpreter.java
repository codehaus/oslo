package org.oslo.console.cli;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 24, 2003
 * Time: 4:39:26 PM
 * To change this template use Options | File Templates.
 */
public interface CommandLineInterpreter {

    public String[] getCommandHelp();

    public String execute(String command) throws Exception;

    public String[] getCommands();
}
