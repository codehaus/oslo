package org.oslo.server.prevayler.transaction.process;

import org.oslo.server.prevayler.system.RantSystem;
import org.oslo.server.prevayler.datamodel.process.Process;
import org.prevayler.util.TransactionWithQuery;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 18, 2003
 * Time: 6:05:21 PM
 * To change this template use Options | File Templates.
 */
public class ProcessUpdateTransaction extends TransactionWithQuery {
    private Process _process;

    public ProcessUpdateTransaction(Process process) {
        this._process = process;
    }

    protected Object executeAndQuery(Object system) throws Exception {
        // Get system
        RantSystem rantSystem = (RantSystem) system;

        System.out.println("Updating process with processid = " + _process.getProcessID());

        if (!rantSystem.checkUpdateProcess(_process))
            throw new IllegalArgumentException("Process does not exist");

        rantSystem.updateProcess(_process);
        return _process;
    }
}
