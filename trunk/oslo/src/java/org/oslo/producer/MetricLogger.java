/**
 * Created by IntelliJ IDEA.
 * User: ac08683
 * Date: 29.apr.2003
 * Time: 13:11:03
 * To change this template use Options | File Templates.
 */
package org.oslo.producer;

import org.oslo.server.prevayler.datamodel.metric.Metric;
import org.oslo.server.plugin.Plugin;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class MetricLogger {
    private static MetricLogger ourInstance;
    private static Socket socket;
    private static BufferedOutputStream bufferedOutputStream;
    private static BufferedInputStream bufferedInputStream;
    private static String processId = null;
    private static HashMap plugins = new HashMap();

    public synchronized static MetricLogger getInstance() {
        if (ourInstance == null) {
            ourInstance = new MetricLogger();
        }
        return ourInstance;
    }

    private MetricLogger() {
        // Ok we need to discover all available plugins that can process data.
        // Each plugin is identified by its matching Advice
        // The plugin contains the needed information to process the incoming data and dispatch it using
        // Common socket connections.

        try {
            //Initialize the socket
            socket = new Socket("127.0.0.1", 8010);
            bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            bufferedInputStream = new BufferedInputStream(socket.getInputStream());

            //Create outputStream
            byte[] inBytes = null;

            // Ok try to get a sequence number, timeout after ten seconds.
            long currentTime = System.currentTimeMillis() + 10000;

            while (System.currentTimeMillis() < currentTime) {
                if (bufferedInputStream.available() != 0) {
                    inBytes = new byte[bufferedInputStream.available()];
                    bufferedInputStream.read(inBytes);
                    currentTime -= 10000;
                } else {
                    wait(100);
                }
            }

            if (inBytes != null) {
                processId = new String(inBytes);
            }

            //outSequence = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("sequence.txt")));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    public void registerPlugin(Plugin plugin) {
        String pluginClassName = plugin.getClass().getName();

        // Add the plugin to the map, allowing us to call and process metrics
        if(!plugins.containsKey(pluginClassName)) {
            plugin.setProcessId(getProcessId());
            plugins.put(pluginClassName, plugin);
        }
    }

    private String getProcessId() {
        return processId;
    }

    // Logs the metric to the actual out stream.
    // Responsibility of the format is left to the actual Plugin itself
    public void logMetric(Metric metric) throws Exception {
        // Ok, we need to fetch the actual plugin class
        String pluginName = metric.getPluginName();

        // Fetching the plugin
        Plugin plugin = (Plugin)plugins.get(pluginName);

        // Set processId on the Metric
        metric.setProcessId(plugin.getProcessId());

        // Create metric String
        String metricString = plugin.createMetricString(metric);

        // Ok log the Metric to the server
        writeMetricToSocket(metricString);
    }

    private synchronized void writeMetricToSocket(String metricString) throws Exception {
        // Write out Metric to the outputString
        System.out.println("MetricString: " + metricString);

        String fixedString = metricString + "\n";

        bufferedOutputStream.write(fixedString.getBytes());
        bufferedOutputStream.flush();
    }

    public void finalize() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }
}

