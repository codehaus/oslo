/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 21, 2003
 * Time: 7:38:34 PM
 * To change this template use Options | File Templates.
 */
package org.oslo.server;

import org.oslo.server.metric.Metric;
import org.oslo.server.plugin.Plugin;

import java.util.*;

public class MetricProcesser {
    private static MetricProcesser ourInstance;
    private static HashMap plugins = new HashMap();
    private static ArrayList structure = new ArrayList();

    public synchronized static MetricProcesser getInstance() throws Exception {
        if (ourInstance == null) {
            ourInstance = new MetricProcesser();
        }
        return ourInstance;
    }

    private MetricProcesser() throws Exception {
        // Set up all the plugins we need to create
        ResourceBundle resourceBundle = ResourceBundle.getBundle("rantserver");
        Enumeration keys = resourceBundle.getKeys();

        while (keys.hasMoreElements()) {
            // Get the key
            String key = (String) keys.nextElement();

            // Get the main tag
            String tag = (String)resourceBundle.getString(key.substring(0, key.lastIndexOf(".")) + ".tag");

            // Get the main className
            String className = (String)resourceBundle.getString(key.substring(0, key.lastIndexOf(".")) + ".class");

            if(!plugins.containsKey(tag)) {
                plugins.put(tag, Class.forName(className).newInstance());
                System.out.println("Added pluginclass: " + className + " with tag: " + tag);
            }
        }
    }

    public Metric processMetric(String metricString) throws Exception {
        // Ok we now have the metric String, we need to figure out what kind of plugin this matches too, by
        // checking for the available keys

        // Get all plugin tags and match
        Iterator keys = plugins.keySet().iterator();
        Plugin processPlugin = null;
        Metric metric = null;

        while (keys.hasNext()) {
            String key = (String) keys.next();

            // Ok match the String
            if(metricString.indexOf("[" + key + "]") != -1)
                processPlugin = (Plugin)plugins.get(key);
        }

        if(processPlugin != null) {
            metric = processPlugin.parseMetricString(metricString);
            System.out.println("Received Metric with String: " + processPlugin.createMetricString(metric));
        }

        return metric;
    }
}
