package org.oslo.server.prevayler.transaction.process;

import org.oslo.server.prevayler.datamodel.process.Process;
import org.oslo.server.prevayler.system.RantSystem;
import org.prevayler.util.TransactionWithQuery;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 25, 2003
 * Time: 10:13:26 PM
 * To change this template use Options | File Templates.
 */
public class ProcessRemoveTransaction extends TransactionWithQuery {
    private Process _process;

    public ProcessRemoveTransaction(Process process) {
        this._process = process;
    }

    protected Object executeAndQuery(Object system) throws Exception {
        // Get system
        RantSystem rantSystem = (RantSystem) system;

        if (rantSystem.getProcess(this._process.getProcessID()) == null)
            throw new IllegalArgumentException("Process does not exits");

        rantSystem.removeProcess(this._process);
        return _process;
    }
}
