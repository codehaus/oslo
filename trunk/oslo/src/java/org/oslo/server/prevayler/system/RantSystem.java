package org.oslo.server.prevayler.system;

import org.prevayler.util.clock.ClockedSystem;
import org.prevayler.util.clock.Clock;

import java.util.HashMap;
import java.util.Date;
import org.oslo.server.prevayler.datamodel.process.Process;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 7, 2003
 * Time: 11:28:41 PM
 * To change this template use Options | File Templates.
 */
public class RantSystem implements ClockedSystem {

    private final HashMap performanceMetrics = new HashMap();
    private final HashMap byClass = new HashMap();
    private final HashMap processes = new HashMap();
    private static long processId = 0;

/*    public PerformanceMetric getPerformanceMetric(int id) {
        return (PerformanceMetric) performanceMetrics.get(new Integer(id));
    }

    public void addPerformanceMetric(PerformanceMetric performanceMetric) {
        performanceMetrics.put(new Integer(performanceMetric.getId()), performanceMetric);

        if (!byClass.containsKey(performanceMetric.getClassName()))
            byClass.put(performanceMetric.getClassName(), performanceMetric);
    }

    public Map getPerformanceMetricsByClass() {
        return byClass;
    }

    public int nextPerformanceMetricId() {
        return performanceMetrics.size();
    }*/


    public void advanceClockTo(Date date) {
    }

    public Clock clock() {
        return null;
    }

    /**
     * Process related issues
     */
    public void addProcess(Process process) throws Exception {
        processes.put(process.getProcessID(), process);
    }

    public synchronized String nextProcessId() {
        return Long.toString(processId++);
    }

    public Process getProcess(String processId) {
        return (Process)processes.get(processId);
    }

    /**
     * Methods for checking consistence of data in the prevayler system
     */
    public boolean checkCreateProcess(String processId) {
        if(!processes.containsKey(processId))
            return true;

        return false;
    }

    // Ensure the integrity of data being stored
    /**public boolean checkCreatePerformanceMetric(int id, String className, String methodeName, String ip, long measurementDateMSec, long innTimeMSec, long outTimeMSec) {
        if (className == null || className.trim().equals(""))
            return false;

        if (methodeName == null || methodeName.trim().equals(""))
            return false;

        if (ip == null || ip.trim().equals(""))
            return false;

        if (getPerformanceMetric(id) != null)
            return false;

        return true;
    }  **/
}
