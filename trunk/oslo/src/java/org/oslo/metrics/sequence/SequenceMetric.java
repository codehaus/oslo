package org.oslo.metrics.sequence;

import org.oslo.server.metric.Metric;
import org.oslo.plugins.sequence.SequencePlugin;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 14, 2003
 * Time: 11:28:05 PM
 * To change this template use Options | File Templates.
 */
public class SequenceMetric extends Metric {
    private String fromMethodName;
    private String fromClassName;
    private String toMethodName;
    private String toMethodClass;

    public SequenceMetric(String fromMethodName, String fromClassName, String toMethodName, String toMethodClass) {
        this.fromMethodName = fromMethodName;
        this.fromClassName = fromClassName;
        this.toMethodName = toMethodName;
        this.toMethodClass = toMethodClass;
    }

    public String getFromMethodName() {
        return fromMethodName;
    }

    public void setFromMethodName(String fromMethodName) {
        this.fromMethodName = fromMethodName;
    }

    public String getFromClassName() {
        return fromClassName;
    }

    public void setFromClassName(String fromClassName) {
        this.fromClassName = fromClassName;
    }

    public String getToMethodName() {
        return toMethodName;
    }

    public void setToMethodName(String toMethodName) {
        this.toMethodName = toMethodName;
    }

    public String getToMethodClass() {
        return toMethodClass;
    }

    public void setToMethodClass(String toMethodClass) {
        this.toMethodClass = toMethodClass;
    }
}
