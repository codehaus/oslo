package org.oslo.server.plugin;

import org.oslo.server.metric.Metric;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 14, 2003
 * Time: 11:20:23 PM
 * To change this template use Options | File Templates.
 */
public interface Plugin {

    public String getIdentifier();

    public String getProcessId();

    public void setProcessId(String processId);

    public Metric parseMetricString(String metricString) throws Exception;

    public String createMetricString(Metric metric) throws Exception;
}
