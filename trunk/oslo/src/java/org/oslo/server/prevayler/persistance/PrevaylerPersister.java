/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 11, 2003
 * Time: 5:47:51 PM
 * To change this template use Options | File Templates.
 */
package org.oslo.server.prevayler.persistance;

import org.prevayler.implementation.SnapshotPrevayler;
import org.prevayler.util.clock.ClockActor;
import org.prevayler.Prevayler;
import org.oslo.server.prevayler.system.RantSystem;

import java.io.IOException;

public class PrevaylerPersister {
    private static PrevaylerPersister prevaylerPersister;
    private static SnapshotPrevayler snapshotPrevayler;

    private PrevaylerPersister() {
        try {
            // Create the snapshot, or connect to the existing one
            snapshotPrevayler = new SnapshotPrevayler(new RantSystem(), "./database/prevaylerBase");
            // Ensures that the clocked system works correctly, introducing a clock keeper.
            ClockActor clockActor = new ClockActor(snapshotPrevayler);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    public synchronized static PrevaylerPersister getInstance() {
        if (prevaylerPersister == null) {
            prevaylerPersister = new PrevaylerPersister();
        }
        return prevaylerPersister;
    }

    public Prevayler getPrevayler() {
        return snapshotPrevayler;
    }
}

