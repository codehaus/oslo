package org.oslo.plugins.sequenceplugin.cli;

import org.oslo.common.prevayler.persistance.PrevaylerPersister;
import org.oslo.common.prevayler.system.RantSystem;
import org.oslo.common.prevayler.transaction.process.ProcessCreateTransaction;
import org.oslo.common.prevayler.transaction.process.ProcessRemoveTransaction;
import org.oslo.common.datamodel.group.MetricGroup;
import org.oslo.common.datamodel.process.Process;
import org.oslo.plugins.sequenceplugin.plugin.SequencePlugin;
import org.oslo.plugins.sequenceplugin.metric.SequenceMetric;
import org.prevayler.Prevayler;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.io.File;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jul 25, 2003
 * Time: 3:10:20 PM
 * To change this template use Options | File Templates.
 */
public class SequenceConsoleCommandsTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();

        cleanUpPrevayler();

        // Ok create a prevayler object and populate it with testdata
        PrevaylerPersister prevaylerPersister = PrevaylerPersister.getInstance();
        Prevayler prevayler = prevaylerPersister.getPrevayler();

        MetricGroup metricGroup = new MetricGroup(SequencePlugin.class.getName());

        SequenceMetric sequenceMetric = new SequenceMetric("1", "org.MainClass", "main", "org.Level1", "level1", "void", new ArrayList());
        metricGroup.addMetric(sequenceMetric);
        sequenceMetric = new SequenceMetric("2", "org.Level1", "level1", "org.Level2", "level2", "void", new ArrayList());
        metricGroup.addMetric(sequenceMetric);
        sequenceMetric = new SequenceMetric("3", "org.MainClass", "main", "org.MainClass", "level0", "void", new ArrayList());
        metricGroup.addMetric(sequenceMetric);

        Process process = new Process("1");
        process.addMetricGroup(metricGroup);
        ProcessCreateTransaction processCreateTransaction = new ProcessCreateTransaction(process);
        processCreateTransaction.executeUsing(prevayler);
        prevaylerPersister.save();
    }

    private void cleanUpPrevayler() {
        // Delete the prevayler, then set it up
        File dir = new File("./database/prevaylerBase");

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                file.delete();
            }
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();

        // Ok create a prevayler object and populate it with testdata
        PrevaylerPersister prevaylerPersister = PrevaylerPersister.getInstance();
        Prevayler prevayler = prevaylerPersister.getPrevayler();
        Process process = new Process("1");
        ProcessRemoveTransaction processRemoveTransaction = new ProcessRemoveTransaction(process);
        processRemoveTransaction.executeUsing(prevayler);
        prevaylerPersister.save();

        cleanUpPrevayler();
    }

    public void testExecuteGenerateSequenceDiagram() throws Exception {
        /*SequenceConsoleCommands sequenceConsoleCommands = new SequenceConsoleCommands();
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


        assertEquals("(org.MainClass main\n\t(org.Level1 level1<> \"void\" \n\t\t(org.Level2 level2<> \"void\" ))\n\t(org.MainClass level0<> \"void\" )\n)", sequenceDiagram);*/
    }
}
