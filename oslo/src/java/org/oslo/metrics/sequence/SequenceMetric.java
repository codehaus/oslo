package org.oslo.metrics.sequence;

import org.oslo.server.prevayler.datamodel.metric.Metric;
import org.oslo.plugins.sequence.SequencePlugin;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 14, 2003
 * Time: 11:28:05 PM
 * To change this template use Options | File Templates.
 */
public class SequenceMetric extends Metric implements Serializable {
    private String callerClass;
    private String callerMethod;
    private String calleeClass;
    private String calleeMethod;
    private ArrayList parameters;
    private String returnType;

    public SequenceMetric(String key, String callerClass, String callerMethod, String calleeClass, String calleeMethod, String returnType, ArrayList parameters) {
        super(key);

        this.returnType = returnType;
        this.parameters = parameters;
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

    public void addParameter(String[] paramter) {
        parameters.add(paramter);
    }

    public ArrayList getParameters() {
        return this.parameters;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
