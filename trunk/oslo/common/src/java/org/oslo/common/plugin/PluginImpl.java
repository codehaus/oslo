package org.oslo.common.plugin;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jul 23, 2003
 * Time: 1:48:24 PM
 * To change this template use Options | File Templates.
 */
public abstract class PluginImpl implements Plugin {
    public String getProcessId(String metricString) {
        int processIdPosition = metricString.indexOf(" ");
        return metricString.substring(0, processIdPosition);
    }

    public String getDataString(String metricString) {
        // Ok remove the tags from the front and back from the data
        int sequenceStartPosition = metricString.indexOf("[" + getIdentifier()  + "]");
        int sequenceEndPosition = metricString.indexOf("[/" + getIdentifier() + "]");

        return metricString.substring(sequenceStartPosition + getIdentifier().length() + 2 + 1, sequenceEndPosition);
    }
}
