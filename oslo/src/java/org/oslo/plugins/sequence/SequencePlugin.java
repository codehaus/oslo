package org.oslo.plugins.sequence;

import org.oslo.server.plugin.Plugin;
import org.oslo.server.metric.Metric;
import org.oslo.metrics.sequence.SequenceMetric;

import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 14, 2003
 * Time: 11:25:25 PM
 * To change this template use Options | File Templates.
 */
public class SequencePlugin implements Plugin {
    private static final String IDENTIFIER = "Sequence";
    private String processId;

    public String getIdentifier() {
        return IDENTIFIER;
    }

    /**
     *
     * @param metricString
     * @return
     * @throws Exception
     *
     * The dataformat is of the following type [Sequence] className methodName callerClass callerMethod [/Sequence]
     */
    public Metric parseMetricString(String metricString) throws Exception {
        // Ok parse the metric
        // Its on the form [IDENTIFIER] Data Data Data Data .... [/IDENTIFIER]
        String processId = getProcessId(metricString);
        String dataString = getDataString(metricString);

        StringTokenizer tokenizer = new StringTokenizer(dataString, " ");

        // Ok get all the data we need, we know how many elements there are
        String fromMethodName = tokenizer.nextToken();
        String fromClassName = tokenizer.nextToken();
        String toMethodName = tokenizer.nextToken();
        String toClassName = tokenizer.nextToken();

        // Ok we need to construct a Metric object
        SequenceMetric sequenceMetric =  new SequenceMetric(fromMethodName, fromClassName, toMethodName, toClassName);
        // Add the process Id number so that we can have a link between all objects for a session
        sequenceMetric.setProcessId(processId);
        return sequenceMetric;
    }

    public String getProcessId(String metricString) {
        int processIdPosition = metricString.indexOf(" ");
        return metricString.substring(0, processIdPosition);
    }

    public String getDataString(String metricString) {
        // Ok remove the tags from the front and back from the data
        int sequenceStartPosition = metricString.indexOf("[" + IDENTIFIER + "]");
        int sequenceEndPosition = metricString.indexOf("[/" + IDENTIFIER + "]");

        return metricString.substring(sequenceStartPosition + IDENTIFIER.length() + 2 + 1, sequenceEndPosition - 1);
    }

    public String createMetricString(Metric metric) throws Exception {
        SequenceMetric sequenceMetric = (SequenceMetric)metric;
        return sequenceMetric.getProcessId() + " [Sequence] " + sequenceMetric.getFromMethodName() + " " + sequenceMetric.getFromClassName() + " " + sequenceMetric.getToMethodClass() + " " + sequenceMetric.getToMethodClass() + "[/Sequence]";
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
