package org.oslo.metrics.sequence;

import org.oslo.server.prevayler.datamodel.metric.Metric;
import org.oslo.plugins.sequence.SequencePlugin;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 14, 2003
 * Time: 11:28:05 PM
 * To change this template use Options | File Templates.
 */
public class SequenceMetric extends Metric {
    private String callerClass;
    private String callerMethod;
    private String calleeClass;
    private String calleeMethod;

    public SequenceMetric(String key, String callerClass, String callerMethod, String calleeClass, String calleeMethod) {
        super(key);

        this.callerClass = callerClass;
        this.callerMethod = callerMethod;
        this.calleeClass = calleeClass;
        this.calleeMethod = calleeMethod;
    }

    public String getCallerClass() {
        return callerClass;
    }

    public void setCallerClass(String callerClass) {
        this.callerClass = callerClass;
    }

    public String getCallerMethod() {
        return callerMethod;
    }

    public void setCallerMethod(String callerMethod) {
        this.callerMethod = callerMethod;
    }

    public String getCalleeClass() {
        return calleeClass;
    }

    public void setCalleeClass(String calleeClass) {
        this.calleeClass = calleeClass;
    }

    public String getCalleeMethod() {
        return calleeMethod;
    }

    public void setCalleeMethod(String calleeMethod) {
        this.calleeMethod = calleeMethod;
    }
}
