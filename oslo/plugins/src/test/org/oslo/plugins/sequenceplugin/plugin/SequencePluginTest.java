package org.oslo.plugins.sequenceplugin.plugin;

import junit.framework.TestCase;
import org.prevayler.Prevayler;
import org.oslo.common.datamodel.process.Process;
import org.oslo.common.datamodel.group.MetricGroup;
import org.oslo.common.prevayler.persistance.PrevaylerPersister;
import org.oslo.common.prevayler.transaction.process.ProcessCreateTransaction;
import org.oslo.common.prevayler.transaction.process.ProcessRemoveTransaction;
import org.oslo.common.prevayler.system.RantSystem;
import org.oslo.plugins.sequenceplugin.metric.SequenceMetric;

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
}

