package org.oslo.server;

import junit.framework.TestCase;
import org.oslo.server.prevayler.datamodel.metric.Metric;
import org.oslo.metrics.sequence.SequenceMetric;

import java.util.ArrayList;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 25, 2003
 * Time: 10:00:23 PM
 * To change this template use Options | File Templates.
 */
public class MetricProcesserTest extends TestCase {
    public void testProcessMetric() throws Exception {
        MetricProcesser metricProcesser = MetricProcesser.getInstance();
        assertNotNull(metricProcesser);

        //SequenceMetric sequenceMetric = new SequenceMetric("fromMethodName", "fromClassName", "toMethodName", "toMethodClass");
        Metric metric = metricProcesser.processMetric("1234567 [Sequence] fromMethod fromClass toMethod toClass void [/Sequence]");
        assertNotNull(metric);

        assertTrue(metric instanceof SequenceMetric);
    }

    public void testParseMetrics() throws Exception {
        MetricProcesser metricProcesser = MetricProcesser.getInstance();
        assertNotNull(metricProcesser);

        String metricString = "1055109541319 [Sequence] org.oslo.sample.SampleMain doSomething org.oslo.sample.SampleLevel1 doSomethingLevel1[/Sequence] 1055109541319 [Sequence] org.oslo.sample.SampleLevel1 doSomethingLevel1 org.oslo.sample.SampleLevel2 doSomethingLevel2nr1[/Sequence] 1055109541319 [Sequence] org.oslo.sample.SampleLevel1 doSomethingLevel1 org.oslo.sample.SampleLevel2 doSomethingLevel2nr2[/Sequence] 1055109541319 [Sequence] org.oslo.sample.SampleMain doSomething org.oslo.sample.SampleLevel1 doSomethingLevel2_nr2[/Sequence]";

        ArrayList metrics = metricProcesser.parseMetrics(metricString);
        assertEquals("1055109541319 [Sequence] org.oslo.sample.SampleMain doSomething org.oslo.sample.SampleLevel1 doSomethingLevel1[/Sequence]", (String) metrics.get(0));
        assertEquals("1055109541319 [Sequence] org.oslo.sample.SampleLevel1 doSomethingLevel1 org.oslo.sample.SampleLevel2 doSomethingLevel2nr1[/Sequence]", (String) metrics.get(1));
        assertEquals("1055109541319 [Sequence] org.oslo.sample.SampleLevel1 doSomethingLevel1 org.oslo.sample.SampleLevel2 doSomethingLevel2nr2[/Sequence]", (String) metrics.get(2));
        assertEquals("1055109541319 [Sequence] org.oslo.sample.SampleMain doSomething org.oslo.sample.SampleLevel1 doSomethingLevel2_nr2[/Sequence]", (String) metrics.get(3));
    }
}
