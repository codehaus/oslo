package org.oslo.server.prevayler.transaction.process;

import org.prevayler.util.TransactionWithQuery;
import org.oslo.server.prevayler.system.RantSystem;
import org.oslo.common.datamodel.process.Process;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 8, 2003
 * Time: 8:53:49 PM
 * To change this template use Options | File Templates.
 */
public class ProcessCreateTransaction extends TransactionWithQuery {
    private Process _process;

    public ProcessCreateTransaction(Process process) {
        this._process = process;
    }

    protected Object executeAndQuery(Object system) throws Exception {
        // Get system
        RantSystem rantSystem = (RantSystem) system;

        //System.out.println("Creating process with processid = " + _process.getProcessID());

        if (!rantSystem.checkCreateProcess(_process))
            throw new IllegalArgumentException("Illegal process ID");

        //Process process = new Process(_process);
        rantSystem.addProcess(_process);
        return _process;
    }
}
