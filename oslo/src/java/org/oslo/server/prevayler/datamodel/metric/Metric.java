package org.oslo.server.prevayler.datamodel.metric;

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
    private String key;

    public Metric(String key) {
        this.key = key;
    }

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

    public String getKey() {
        return key;
    }
}
