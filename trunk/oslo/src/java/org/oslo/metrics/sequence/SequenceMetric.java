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
    private String targetClass;
    private String targetMethodName;

    public SequenceMetric(String targetClass, String targetMethodName) {
        this.targetClass = targetClass;
        this.targetMethodName = targetMethodName;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetMethodName() {
        return targetMethodName;
    }

    public void setTargetMethodName(String targetMethodName) {
        this.targetMethodName = targetMethodName;
    }
}
