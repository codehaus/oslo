package org.oslo.server;

import junit.framework.TestCase;
import org.oslo.metrics.sequence.SequenceMetric;
import org.oslo.server.metric.Metric;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 21, 2003
 * Time: 7:54:12 PM
 * To change this template use Options | File Templates.
 */
public class MetricProcesserTest extends TestCase {
    public void testProcessMetric() throws Exception {
        MetricProcesser metricProcesser = MetricProcesser.getInstance();
        assertNotNull(metricProcesser);

        //SequenceMetric sequenceMetric = new SequenceMetric("fromMethodName", "fromClassName", "toMethodName", "toMethodClass");
        Metric metric = metricProcesser.processMetric("1234567 [Sequence] fromMethod fromClass toMethod toClass [/Sequence]");
        assertNotNull(metric);

        assertTrue(metric instanceof SequenceMetric);
    }
}
