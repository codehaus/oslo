package org.oslo.server.prevayler.transaction;

import org.prevayler.util.TransactionWithQuery;
import org.oslo.server.infounits.PerformanceMetric;
import org.oslo.server.prevayler.system.RantSystem;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 7, 2003
 * Time: 11:55:24 PM
 * To change this template use Options | File Templates.
 */
public class PerformanceMetricCreateTransaction extends TransactionWithQuery {
    private int _id;
    private String _className;
    private String _methodeName;
    private String _ip;
    private long _measurementDateMSec;
    private long _innTimeMSec;
    private long _outTimeMSec;

    public PerformanceMetricCreateTransaction(int _id, String _className, String _methodeName, String _ip, long _measurementDateMSec, long _innTimeMSec, long _outTimeMSec) {
        this._id = _id;
        this._className = _className;
        this._methodeName = _methodeName;
        this._ip = _ip;
        this._measurementDateMSec = _measurementDateMSec;
        this._innTimeMSec = _innTimeMSec;
        this._outTimeMSec = _outTimeMSec;
    }

    protected Object executeAndQuery(Object system) throws Exception {
        PerformanceMetric performanceMetric = null;
        RantSystem rantSystem = (RantSystem)system;

        if(!rantSystem.checkCreatePerformanceMetric(_id, _className, _methodeName, _ip, _measurementDateMSec, _innTimeMSec, _outTimeMSec)) {
            throw new IllegalArgumentException("Invalid performance metric parameters");
        }

        performanceMetric = new PerformanceMetric(_id, _className, _methodeName, _ip, _measurementDateMSec, _innTimeMSec, _outTimeMSec);
        rantSystem.addPerformanceMetric(performanceMetric);

        return performanceMetric;
    }
}
