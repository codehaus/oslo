package org.oslo.server.prevayler.system;

import org.prevayler.util.clock.ClockedSystem;
import org.oslo.server.infounits.PerformanceMetric;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 7, 2003
 * Time: 11:28:41 PM
 * To change this template use Options | File Templates.
 */
public class RantSystem extends ClockedSystem {

    private final HashMap performanceMetrics = new HashMap();

    public PerformanceMetric getPerformanceMetric(long id) {
        return (PerformanceMetric)performanceMetrics.get(id);
    }

    public void addPerformanceMetric(PerformanceMetric performanceMetric) {
        performanceMetric.put(performanceMetric.getId(), performanceMetric);
    }
}
