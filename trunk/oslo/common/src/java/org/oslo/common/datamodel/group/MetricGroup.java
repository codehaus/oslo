package org.oslo.common.datamodel.group;

import org.oslo.common.datamodel.metric.Metric;

import java.util.HashMap;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 8, 2003
 * Time: 8:28:15 PM
 * To change this template use Options | File Templates.
 */
public class MetricGroup implements Serializable {
    // Include what class type metric this is
    String pluginName;
    HashMap metrics = new HashMap();
    String description;

    public MetricGroup(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void addMetric (Metric metric) {
        metrics.put(new Integer(metrics.size() + 1), metric);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap getMetrics() {
        return metrics;
    }
}
