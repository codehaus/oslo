package org.oslo.server.infounits;

import java.util.Date;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 7, 2003
 * Time: 11:08:34 PM
 * To change this template use Options | File Templates.
 */
public class PerformanceMetric implements Serializable {
    private long measurementDateMSec;
    private String methodeName;
    private long innTimeMSec;
    private long outTimeMSec;
    private String ip;
    private String className;
    private Long id;

    public PerformanceMetric(Long id, long measurementDateMSec, String methodeName, long innTimeMSec, long outTimeMSec, String ip, String className) {
        this.measurementDateMSec = measurementDateMSec;
        this.methodeName = methodeName;
        this.innTimeMSec = innTimeMSec;
        this.outTimeMSec = outTimeMSec;
        this.ip = ip;
        this.className = className;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
