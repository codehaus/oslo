package org.oslo.plugins.sequence;

import junit.framework.TestCase;
import org.oslo.metrics.sequence.SequenceMetric;
import org.oslo.server.prevayler.persistance.PrevaylerPersister;
import org.oslo.server.prevayler.system.RantSystem;
import org.prevayler.Prevayler;
import org.oslo.server.prevayler.datamodel.process.Process;
import org.oslo.server.prevayler.datamodel.group.MetricGroup;
import org.oslo.server.prevayler.transaction.process.ProcessCreateTransaction;
import org.oslo.server.prevayler.transaction.process.ProcessRemoveTransaction;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 24, 2003
 * Time: 10:10:32 PM
 * To change this template use Options | File Templates.
 */
public class SequencePluginTest extends TestCase {
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

    public void testParseMetricString() throws Exception {
        SequencePlugin sequencePlugin = new SequencePlugin();
        SequenceMetric sequenceMetric = (SequenceMetric) sequencePlugin.parseMetricString("1234567 [Sequence] toClass toMethod fromClass fromMethod void[/Sequence]");

        assertNotNull(sequenceMetric);

        // Validate that the information is correct
        assertEquals("toClass", sequenceMetric.getCallerClass());
        assertEquals("toMethod", sequenceMetric.getCallerMethod());
        assertEquals("fromClass", sequenceMetric.getCalleeClass());
        assertEquals("fromMethod", sequenceMetric.getCalleeMethod());
        assertEquals("1234567", sequenceMetric.getProcessId());
        assertEquals("void", sequenceMetric.getReturnType());
    }

    public void testGetIdentifier() throws Exception {
        SequencePlugin sequencePlugin = new SequencePlugin();
        assertEquals(sequencePlugin.getIdentifier(), "Sequence");
    }

    public void testGetIdNumber() throws Exception {
        SequencePlugin sequencePlugin = new SequencePlugin();
        SequenceMetric sequenceMetric = (SequenceMetric) sequencePlugin.parseMetricString("1234567 [Sequence] fromMethod fromClass toMethod toClass void[/Sequence]");
        assertNotNull(sequenceMetric);

        // Get sequence String
        String sequenceString = sequencePlugin.createMetricString(sequenceMetric);
        assertNotNull(sequenceString);

        String processId = sequencePlugin.getProcessId(sequenceString);

        assertNotNull(processId);
        assertEquals("1234567", processId);
    }

    public void testExecuteGenerateSequenceDiagram() throws Exception {
        SequencePlugin sequencePlugin = new SequencePlugin();
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
        sequenceDiagram = sequencePlugin.executeGenerateSequenceDiagram(tokenizer);

        assertNotNull(sequenceDiagram);



        assertEquals("(org.MainClass main\n\t(org.Level1 level1<> \"void\" \n\t\t(org.Level2 level2<> \"void\" ))\n\t(org.MainClass level0<> \"void\" )\n)", sequenceDiagram);
    }
}
