package org.oslo.producer;

import junit.framework.TestCase;
import org.oslo.plugins.sequence.SequencePlugin;
import org.oslo.metrics.sequence.SequenceMetric;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 20, 2003
 * Time: 8:28:52 PM
 * To change this template use Options | File Templates.
 */
public class MetricLoggerTest extends TestCase {

    public void testLogMetric() throws Exception {
        MetricLogger metricLogger = MetricLogger.getInstance();

        //Register the plugin, so that the logger can use it to process the needed information
        SequencePlugin sequencePlugin = new SequencePlugin();

        assertNotNull(sequencePlugin);

        // Register plugin
        metricLogger.registerPlugin(sequencePlugin);

        // Ok generate a fake Metric for the Sequence Plugin
        SequenceMetric sequenceMetric = new SequenceMetric("targetClass", "targetMethodName");
        sequenceMetric.setPluginName(sequencePlugin.getClass().getName());

        metricLogger.logMetric(sequenceMetric);
    }
}
