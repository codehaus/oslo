package org.oslo.plugins.sequence;

import junit.framework.TestCase;
import org.oslo.metrics.sequence.SequenceMetric;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 20, 2003
 * Time: 1:39:50 PM
 * To change this template use Options | File Templates.
 */
public class SequencePluginTest extends TestCase {
    public void testParseMetricString() throws Exception {
        SequencePlugin sequencePlugin = new SequencePlugin();
        SequenceMetric sequenceMetric = (SequenceMetric)sequencePlugin.parseMetricString("1234567 [Sequence] fromMethod fromClass toMethod toClass [/Sequence]");

        assertNotNull(sequenceMetric);

        // Validate that the information is correct
        assertEquals("fromMethod", sequenceMetric.getFromMethodName());
        assertEquals("fromClass", sequenceMetric.getFromClassName());
        assertEquals("toMethod", sequenceMetric.getToMethodName());
        assertEquals("toClass", sequenceMetric.getToMethodClass());
        assertEquals("1234567", sequenceMetric.getProcessId());
    }

    public void testGetDataString() throws Exception {
        SequencePlugin sequencePlugin = new SequencePlugin();
        String dataString = sequencePlugin.getDataString("[Sequence] method1 method2 [/Sequence]");
        assertEquals("method1 method2", dataString);
    }

    public void testGetIdentifier() throws Exception {
        SequencePlugin sequencePlugin = new SequencePlugin();
        assertEquals(sequencePlugin.getIdentifier(), "Sequence");
    }

    public void testGetIdNumber() throws Exception {
        SequencePlugin sequencePlugin = new SequencePlugin();
        SequenceMetric sequenceMetric = (SequenceMetric)sequencePlugin.parseMetricString("1234567 [Sequence] fromMethod fromClass toMethod toClass [/Sequence]");
        assertNotNull(sequenceMetric);

        // Get sequence String
        String sequenceString = sequencePlugin.createMetricString(sequenceMetric);
        assertNotNull(sequenceString);

        String processId =  sequencePlugin.getProcessId(sequenceString);

        assertNotNull(processId);
        assertEquals("1234567", processId);
    }
}
