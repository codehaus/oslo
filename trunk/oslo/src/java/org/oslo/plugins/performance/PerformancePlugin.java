package org.oslo.plugins.performance;

import org.oslo.common.plugin.Plugin;
import org.oslo.common.plugin.PluginImpl;
import org.oslo.common.datamodel.metric.Metric;
import org.oslo.common.cli.CommandLineInterpreter;
import org.oslo.metrics.performance.PerformanceMetric;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jul 23, 2003
 * Time: 1:29:01 PM
 * To change this template use Options | File Templates.
 */
public class PerformancePlugin extends PluginImpl implements Plugin, CommandLineInterpreter {
    private static final String IDENTIFIER = "Performance";
    private static Log log = LogFactory.getLog(PerformancePlugin.class);
    private String processId;

    public String getIdentifier() {
        return IDENTIFIER;  //To change body of implemented methods use Options | File Templates.
    }

    public String getProcessId() {
        return processId;  //To change body of implemented methods use Options | File Templates.
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Metric parseMetricString(String metricString) throws Exception {
        // Ok parse the metric
        // Its on the form [IDENTIFIER] Data Data Data Data .... [/IDENTIFIER]
        String processId = getProcessId(metricString);
        String dataString = getDataString(metricString);

        StringTokenizer tokenizer = new StringTokenizer(dataString, " ");

        // Ok get all the data we need, we know how many elements there are
        String className = tokenizer.nextToken();
        String methodName = tokenizer.nextToken();
        String ip = tokenizer.nextToken();
        String measurementDateMSec = tokenizer.nextToken();
        String innTimeMSec = tokenizer.nextToken();
        String outTimeMSec = tokenizer.nextToken();

        // Ok we need to construct a Metric object
        PerformanceMetric performanceMetric = new PerformanceMetric(measurementDateMSec, className, methodName, ip, Long.parseLong(measurementDateMSec), Long.parseLong(innTimeMSec), Long.parseLong(outTimeMSec));
        performanceMetric.setPluginName(PerformanceMetric.class.getName());

        // Add the process Id number so that we can have a link between all objects for a session
        performanceMetric.setProcessId(processId);
        return performanceMetric;


        //return null;  //To change body of implemented methods use Options | File Templates.
    }

    public String createMetricString(Metric metric) throws Exception {
        PerformanceMetric performanceMetric = (PerformanceMetric) metric;
        String performanceMetricString = performanceMetric.getProcessId() + " [" + IDENTIFIER + "] "
                + performanceMetric.getClassName() + " "
                + performanceMetric.getMethodeName() + " "
                + performanceMetric.getIp() + " "
                + performanceMetric.getMeasurementDateMSec() + " "
                + performanceMetric.getInnTimeMSec() + " "
                + performanceMetric.getOutTimeMSec();

        performanceMetricString += "[/" + IDENTIFIER + "]";
        log.debug("Created performanceString: " + performanceMetricString);

        return performanceMetricString;
    }


    /**
     * Deals with the command line objects
     */
    public String[] getCommandHelp() {
        return new String[0];  //To change body of implemented methods use Options | File Templates.
    }

    public String execute(String command) throws Exception {
        return null;  //To change body of implemented methods use Options | File Templates.
    }

    public String[] getCommands() {
        return new String[0];  //To change body of implemented methods use Options | File Templates.
    }
}
