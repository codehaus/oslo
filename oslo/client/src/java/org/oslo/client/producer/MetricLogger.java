/**
 * Created by IntelliJ IDEA.
 * User: ac08683
 * Date: 29.apr.2003
 * Time: 13:11:03
 * To change this template use Options | File Templates.
 */
package org.oslo.client.producer;

import org.oslo.common.datamodel.metric.Metric;
import org.oslo.common.plugin.Plugin;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MetricLogger {
    private static MetricLogger ourInstance;
    private static Socket socket;
    private static BufferedOutputStream bufferedOutputStream;
    private static BufferedInputStream bufferedInputStream;
    private static String processId = null;
    private static HashMap plugins = new HashMap();
    private static String url = "127.0.0.1";
    private static int port = 8010;
    private static boolean server = true;
    private static String filedirectory = "./";
    private static String fileprefix = "oslo-";
    private static HashMap fileOutputStreams = new HashMap();
    private static Log log = LogFactory.getLog(MetricLogger.class);

    public synchronized static MetricLogger getInstance() {
        if (ourInstance == null) {
            ourInstance = new MetricLogger();

            // Ok, set the server host url and port
            String serverState = System.getProperty("oslo.server.use");
            String portString = System.getProperty("oslo.server.port");
            String filedir = System.getProperty("oslo.file.directory");
            String filepre = System.getProperty("oslo.file.prefix");

            // If we specfied that the server is to be used
            if (serverState != null && !serverState.toUpperCase().equals("FALSE")) {
                // Convert the portString to an int
                try {
                    port = Integer.parseInt(portString);
                    url = System.getProperty("oslo.server.url");
                } catch (NumberFormatException e) {
                    // Ok nothing specified, we use the default values
                    //e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            } else {
                // Check if the user specified a file directory for the metric strings
                if(filedir != null)
                    filedirectory = filedir;

                // Check if the user specified a file prefix
                if(filepre != null)
                    fileprefix = filepre;

                // If we specified that the server support is off, set this to reflect the state
                if(serverState != null && serverState.toUpperCase().equals("FALSE")) {
                    // Ensure that the server is not used
                    server = false;
                }
            }
        }
        return ourInstance;
    }

    private MetricLogger() {
        // Ok we need to discover all available plugins that can process data.
        // Each plugin is identified by its matching Advice
        // The plugin contains the needed information to process the incoming data and dispatch it using
        // Common socket connections.

        // Set up the connection if we specified a loging server
        if (server == true) {
            try {
                //Initialize the socket
                socket = new Socket(url, port);
                bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
                bufferedInputStream = new BufferedInputStream(socket.getInputStream());

                //Create outputStream
                byte[] inBytes = null;

                // Ok try to get a sequenceplugin number, timeout after ten seconds.
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
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        } else {
            // Give us a random processId
            processId = Long.toString(System.currentTimeMillis());

            // Intitalise a file outputstream for each of the metrics available
            Iterator pluginsIterator = plugins.values().iterator();

            while (pluginsIterator.hasNext()) {
                Plugin plugin = (Plugin) pluginsIterator.next();
                String fileOutputName = filedirectory + fileprefix + plugin.getProcessId() + "-" + plugin.getIdentifier();

                // Just ignore the file if it wrong
                try {
                    fileOutputStreams.put(plugin.getIdentifier(), new BufferedOutputStream(new FileOutputStream(fileOutputName)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }
        }
    }

    public void registerPlugin(Plugin plugin) {
        String pluginClassName = plugin.getClass().getName();
        System.out.println("PLUGIN REGISTERED WITH NAME = " + pluginClassName);

        // Add the plugin to the map, allowing us to call and process metrics
        if (!plugins.containsKey(pluginClassName)) {
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
        log.debug("pluginName = " + pluginName);

        // Fetching the plugin
        Plugin plugin = (Plugin) plugins.get(pluginName);
        log.debug("Got plugin");

        // Set processId on the Metric
        metric.setProcessId(plugin.getProcessId());
        log.debug("plugin.getProcessId() = " + plugin.getProcessId());

        // Create metric String
        String metricString = plugin.createMetricString(metric);

        // Ok, we are not using the server just writing to a file specified

        // Logging
        log.debug("Server is state = " + server);
        log.debug("metricString = " + metricString);

        try {
            if (server == true) {
                // Ok log the Metric to the server
                writeMetricToSocket(metricString);
            } else {
                writeMetricToFile(plugin, metricString);
            }
        } catch (Throwable e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    private void writeMetricToFile(Plugin plugin, String metricString) throws Exception {
        // Check that the fileoutput exists
        if(fileOutputStreams.containsKey(plugin.getIdentifier())) {
            // Ok fetch the stream and write the metricString to it
            String fixedString = metricString + "\n";
            BufferedOutputStream bufferedOutputStream = (BufferedOutputStream)fileOutputStreams.get(plugin.getIdentifier());
            bufferedOutputStream.write(fixedString.getBytes());
        }
    }

    private synchronized void writeMetricToSocket(String metricString) throws Exception {
        // Write out Metric to the outputString
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

