package org.oslo.server.metric;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 14, 2003
 * Time: 11:24:15 PM
 * To change this template use Options | File Templates.
 */
public class Metric {
    private String pluginName;
    private String processId;

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
