package org.oslo.client;

import org.prevayler.implementation.SnapshotPrevayler;
import org.prevayler.util.clock.ClockActor;
import org.oslo.rantserver.prevayler.system.RantSystem;
import org.apache.commons.jxpath.JXPathContext;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 11, 2003
 * Time: 10:13:44 PM
 * To change this template use Options | File Templates.
 */
public class PrevaylerTestClient {

    public static void main(String[] args) {
        new PrevaylerTestClient().doQuery();
    }

    public void doQuery() {
        try {
            SnapshotPrevayler snapshotPrevayler = new SnapshotPrevayler(new RantSystem(), "./database/prevaylerBase");

            // Ensures that the clocked system works correctly, introducing a clock keeper.
            ClockActor clockActor = new ClockActor(snapshotPrevayler);

            // Get the system
            RantSystem rantSystem = (RantSystem)snapshotPrevayler.prevalentSystem();

            JXPathContext context = JXPathContext.newContext(rantSystem);

            String value = (String)context.getValue("performanceMetricsByClass[@name='(SampleMain doSomething\n']/methodeName");

            /*Iterator iterator = context.iterate("performanceMetricsByClass[@name='(SampleMain doSomething\n']/className");

            while (iterator.hasNext()) {
                Object o = (Object) iterator.next();
                o.toString();
            } */
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }
}
