package org.oslo.server.prevayler.transaction.process;

import org.prevayler.util.TransactionWithQuery;
import org.oslo.server.prevayler.system.RantSystem;
import org.oslo.server.prevayler.datamodel.process.Process;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 8, 2003
 * Time: 8:53:49 PM
 * To change this template use Options | File Templates.
 */
public class ProcessCreateTransaction extends TransactionWithQuery {
    private String _processID;

    public ProcessCreateTransaction(String processID) {
        this._processID = processID;
    }

    protected Object executeAndQuery(Object system) throws Exception {
        // Get system
        RantSystem rantSystem = (RantSystem) system;

        if (!rantSystem.checkCreateProcess(_processID))
            throw new IllegalArgumentException("Illegal process ID");


        Process process = new Process(_processID);
        rantSystem.addProcess(process);
        return process;
    }
}
