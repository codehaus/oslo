package org.oslo.server.prevayler.datamodel.process;

import org.oslo.server.prevayler.datamodel.group.MetricGroup;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 8, 2003
 * Time: 8:27:40 PM
 * To change this template use Options | File Templates.
 */
public class Process {
    String processID;
    HashMap metricGroups = new HashMap();

    public Process(String processID) {
        this.processID = processID;
    }

    public synchronized MetricGroup addMetricGroup(MetricGroup metricGroup) {
        metricGroups.put(metricGroup.getPluginName(), metricGroup);
        return metricGroup;
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
