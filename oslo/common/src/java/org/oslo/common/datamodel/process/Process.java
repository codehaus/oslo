package org.oslo.common.datamodel.process;

import org.oslo.common.datamodel.group.MetricGroup;
import org.oslo.common.datamodel.metric.Metric;

import java.util.HashMap;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 8, 2003
 * Time: 8:27:40 PM
 * To change this template use Options | File Templates.
 *
 */
public class Process implements Serializable {
    private String processID;
    private HashMap metricGroups = new HashMap();

    public Process(String processID) {
        this.processID = processID;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MetricGroup addMetricGroup(MetricGroup metricGroup) {
        metricGroups.put(metricGroup.getPluginName(), metricGroup);
        return metricGroup;
    }

    public void addMetric(Metric metric) {
        if(metricGroups.containsKey(metric.getPluginName())) {
            MetricGroup metricGroup = (MetricGroup)metricGroups.get(metric.getPluginName());
            metricGroup.addMetric(metric);
        }
    }

    public HashMap getMetricGroups() {
        return metricGroups;
    }

    public String getProcessID() {
        return processID;
    }

    public MetricGroup getMetricGroup(String pluginName) {
        return (MetricGroup)metricGroups.get(pluginName);
    }
}
