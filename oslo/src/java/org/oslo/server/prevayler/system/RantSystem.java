package org.oslo.server.prevayler.system;

import org.prevayler.util.clock.ClockedSystem;
import org.prevayler.util.clock.Clock;

import java.util.HashMap;
import java.util.Date;
import java.util.Iterator;

import org.oslo.server.prevayler.datamodel.process.Process;
import org.oslo.server.prevayler.datamodel.group.MetricGroup;
import org.oslo.server.prevayler.datamodel.metric.Metric;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 7, 2003
 * Time: 11:28:41 PM
 * To change this template use Options | File Templates.
 */
public class RantSystem implements ClockedSystem {

    private final HashMap processes = new HashMap();
    private final HashMap metricgroups = new HashMap();
    private final HashMap metrics = new HashMap();
    private static long processId = 0;

    public void advanceClockTo(Date date) {
    }

    public Clock clock() {
        return null;
    }

    /**
     * Process related issues
     */
    public synchronized void addProcess(Process process) throws Exception {
        processes.put(process.getProcessID(), process);
    }

    public synchronized String nextProcessId() {
        return Long.toString(processId++);
    }

    public synchronized Process getProcess(String processId) {
        return (Process)processes.get(processId);
    }

    public synchronized void updateProcess(Process process) throws Exception {
        processes.put(process.getProcessID(), process);
    }

    public Iterator getProcesses() {
        return processes.values().iterator();
    }

    public synchronized void deleteProcess(Process process) {
        processes.remove(process.getProcessID());
    }

    /**
     * Methods for checking consistence of data in the prevayler system
     */
    public boolean checkCreateProcess(Process process) {
        if(!processes.containsKey(process.getProcessID()))
            return true;

        return false;
    }

    public boolean checkUpdateProcess(Process process) throws Exception {
        if(processes.containsKey(process.getProcessID())) {
            // Ensure that we are updating an existing process not replacing an old one
            //Process oldProcess = (Process)processes.get(process.getProcessID());

            //if(oldProcess.getTimestamp() != process.getTimestamp())
            //    throw new Exception("Tried to replace existing process with new, check id of current process");
            return true;
        } else {
            return false;
        }
    }

    public void removeProcess(Process process) {
        if(processes.containsKey(process.getProcessID()))
            processes.remove(process.getProcessID());
    }

}
