package org.oslo.plugins.sequenceplugin.cli;

import org.oslo.rantserver.prevayler.persistance.PrevaylerPersister;
import org.oslo.rantserver.prevayler.system.RantSystem;
import org.prevayler.Prevayler;

import java.util.Iterator;
import java.util.StringTokenizer;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jul 25, 2003
 * Time: 3:10:20 PM
 * To change this template use Options | File Templates.
 */
public class SequenceConsoleCommandsTest extends TestCase {
    public void testExecuteGenerateSequenceDiagram() throws Exception {
        SequenceConsoleCommands sequenceConsoleCommands = new SequenceConsoleCommands();
        //String command = "GENERATESEQUENCEDIAGRAM <processID> <startingClass> <startingMentho>";

        PrevaylerPersister prevaylerPersister = PrevaylerPersister.getInstance();
        Prevayler prevayler = prevaylerPersister.getPrevayler();
        RantSystem rantSystem = (RantSystem) prevayler.prevalentSystem();

        Iterator processes = rantSystem.getProcesses();

        assertNotNull(processes);

        String sequenceDiagram = null;

        // Generate commands
        String command = "1" + " org.MainClass main";
        StringTokenizer tokenizer = new StringTokenizer(command);
        sequenceDiagram = sequenceConsoleCommands.executeGenerateSequenceDiagram(tokenizer);

        assertNotNull(sequenceDiagram);


        assertEquals("(org.MainClass main\n\t(org.Level1 level1<> \"void\" \n\t\t(org.Level2 level2<> \"void\" ))\n\t(org.MainClass level0<> \"void\" )\n)", sequenceDiagram);
    }
}
