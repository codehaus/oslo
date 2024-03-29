package org.oslo.metrics.performance;

import org.oslo.common.datamodel.metric.Metric;

import java.util.Date;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 7, 2003
 * Time: 11:08:34 PM
 * To change this template use Options | File Templates.
 */
public class PerformanceMetric extends Metric implements Serializable {
    private String className;
    private String methodeName;
    private String ip;
    private long measurementDateMSec;
    private long innTimeMSec;
    private long outTimeMSec;

   public PerformanceMetric(String key, String className, String methodeName, String ip, long measurementDateMSec, long innTimeMSec, long outTimeMSec) {
        super(key);
        this.className = className;
        this.methodeName = methodeName;
        this.ip = ip;
        this.measurementDateMSec = measurementDateMSec;
        this.innTimeMSec = innTimeMSec;
        this.outTimeMSec = outTimeMSec;
    }

    public long getMeasurementDateMSec() {
        return measurementDateMSec;
    }

    public void setMeasurementDateMSec(long measurementDateMSec) {
        this.measurementDateMSec = measurementDateMSec;
    }

    public String getMethodeName() {
        return methodeName;
    }

    public void setMethodeName(String methodeName) {
        this.methodeName = methodeName;
    }

    public long getInnTimeMSec() {
        return innTimeMSec;
    }

    public void setInnTimeMSec(long innTimeMSec) {
        this.innTimeMSec = innTimeMSec;
    }

    public long getOutTimeMSec() {
        return outTimeMSec;
    }

    public void setOutTimeMSec(long outTimeMSec) {
        this.outTimeMSec = outTimeMSec;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
