/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 21, 2003
 * Time: 7:38:34 PM
 * To change this template use Options | File Templates.
 */
package org.oslo.server;

import org.oslo.server.prevayler.datamodel.metric.Metric;
import org.oslo.server.plugin.Plugin;

import java.util.*;
import java.util.jar.JarInputStream;
import java.util.jar.JarEntry;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;

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
        /*ResourceBundle resourceBundle = ResourceBundle.getBundle("rantserver");
        Enumeration keys = resourceBundle.getKeys();

        while (keys.hasMoreElements()) {
            // Get the key
            String key = (String) keys.nextElement();

            // Get the main tag
            String tag = (String) resourceBundle.getString(key.substring(0, key.lastIndexOf(".")) + ".tag");

            // Get the main className
            String className = (String) resourceBundle.getString(key.substring(0, key.lastIndexOf(".")) + ".class");

            if (!plugins.containsKey(tag)) {
                plugins.put(tag, Class.forName(className).newInstance());
                System.out.println("Added pluginclass: " + className + " with tag: " + tag);
            }
        } */
        ArrayList pluginsList = PluginDiscoveryService.discoverPlugins();

        //System.out.println("Hello");
        for (Iterator iterator = pluginsList.iterator(); iterator.hasNext();) {
            String plugin = (String) iterator.next();

            if (!plugins.containsKey(plugin)) {
                Plugin pluginInstance = (Plugin)Class.forName(plugin).newInstance();

                plugins.put(pluginInstance.getIdentifier(), pluginInstance);
                System.out.println("Added pluginclass: " + plugin + " with tag: " + pluginInstance.getIdentifier());
            }
        }

        // Discover all available plugins in the classpath and register them with the server
        //System.getProperty()
    }



    public ArrayList parseMetrics(String metricString) throws Exception {
        // Ok we now have the metric String, we need to figure out what kind of plugin this matches too, by
        // checking for the available keys

        ArrayList metricStrings = new ArrayList();

        while (metricString.indexOf("[") != -1) {
            // Get the start position
            //int startIndex = metricStrings.indexOf("[");
            int startOfEndIndex = metricString.indexOf("[/");
            int endIndex = metricString.indexOf("]", startOfEndIndex) + 1;

            String parseString = metricString.substring(0, endIndex).trim();
            metricStrings.add(parseString);

            // Remove the metric String from the collection
            metricString = metricString.substring(endIndex);
        }


        return metricStrings;

        // Get all plugin tags and match
        /*Iterator keys = plugins.keySet().iterator();
        Plugin processPlugin = null;
        Metric metric = null;*/

        /*(while (keys.hasNext()) {
           String key = (String) keys.next();
            int startIndex = metricString.indexOf("[" + key + "]");

            // Ok match the String
            if (startIndex != -1) {
                // Ok pick out the string
                int endIndex = metricString.indexOf("[/" + key + "]") + key.length() + 3;
                String parsedMetricString = metricString.substring(startIndex, endIndex);
                metricStrings.add(parsedMetricString);

                // Remove the string
                metricString = metricString.substring(endIndex);
            }
        } */

        /*if (processPlugin != null) {
            metric = processPlugin.parseMetricString(metricString);
            System.out.println("Received Metric with String: " + processPlugin.createMetricString(metric));
        } */
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
            if (metricString.indexOf("[" + key + "]") != -1)
                processPlugin = (Plugin) plugins.get(key);
        }

        if (processPlugin != null) {
            metric = processPlugin.parseMetricString(metricString);
            metric.setPluginName(processPlugin.getClass().getName());
            System.out.println("Received Metric with String: " + processPlugin.createMetricString(metric));
        }

        return metric;
    }
}
