package org.oslo.server.thread;

import org.oslo.server.prevayler.persistance.PrevaylerPersister;
import org.oslo.server.prevayler.system.RantSystem;
import org.oslo.server.prevayler.transaction.PerformanceMetricCreateTransaction;
import org.oslo.server.infounits.PerformanceMetric;
import org.prevayler.Prevayler;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;

/**
 * A worker thread class which can drain channels and echo-back
 * the input. Each instance is constructed with a reference to
 * the owning thread pool object. When started, the thread loops
 * forever waiting to be awakened to service the channel associated
 * with a SelectionKey object.
 * The worker is tasked by calling its serviceChannel( ) method
 * with a SelectionKey object. The serviceChannel( ) method stores
 * the key reference in the thread object then calls notify( )
 * to wake it up. When the channel has been drained, the worker
 * thread returns itself to its parent pool.
 */
public class WorkerThread extends Thread {
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private ThreadPool pool;
    private SelectionKey key;

    public WorkerThread(ThreadPool pool) {
        this.pool = pool;
    }

    // Loop forever waiting for work to do
    public synchronized void run() {
        System.out.println(this.getName() + " is ready");
        while (true) {
            try {
                // Sleep and release object lock
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                // Clear interrupt status
                this.interrupted();
            }
            if (key == null) {
                continue; // just in case
            }
            System.out.println(this.getName() + " has been awakened");
            try {
                drainChannel(key);
            } catch (Exception e) {
                System.out.println("Caught '" + e + "' closing channel");

                // Close channel and nudge selector
                try {
                    key.channel().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                key.selector().wakeup();
            }
            key = null;

            // Done. Ready for more. Return to pool
            this.pool.returnWorker(this);
        }
    }

    /**
     * Called to initiate a unit of work by this worker thread
     * on the provided SelectionKey object. This method is
     * synchronized, as is the run( ) method, so only one key
     * can be serviced at a given time.
     * Before waking the worker thread, and before returning
     * to the main selection loop, this key's interest set is
     * updated to remove OP_READ. This will cause the selector
     * to ignore read-readiness for this channel while the
     * worker thread is servicing it.
     */
    public synchronized void serviceChannel(SelectionKey key) {
        this.key = key;
        key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
        this.notify(); // Awaken the thread
    }

    /**
     * The actual code which drains the channel associated with
     * the given key. This method assumes the key has been
     * modified prior to invocation to turn off selection
     * interest in OP_READ. When this method completes it
     * re-enables OP_READ and calls wakeup( ) on the selector
     * so the selector will resume watching this channel.
     */
    public void drainChannel(SelectionKey key) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        SocketChannel channel = (SocketChannel) key.channel();
        int count;
        buffer.clear(); // Empty buffer

        FileOutputStream fileOutputStream = new FileOutputStream("sequence.txt", true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Loop while data is available; channel is nonblocking
        while ((count = channel.read(buffer)) > 0) {
            buffer.flip(); // make buffer readable

            System.out.println("Reading.......");

            // Send the data; may not go all at once
            while (buffer.hasRemaining()) {
                // Open a file channel and append the data
                //RandomAccessFile randomAccessFile = new RandomAccessFile("sequence.txt", )
                //FileOutputStream fileOutputStream = new FileOutputStream("sequence.txt", true);
                //FileChannel fileChannel = fileOutputStream.getChannel();

                //fileChannel.write(buffer);
                //fileChannel.close();
                //fileOutputStream.close();
                //channel.write(buffer);
                //stringBuffer.append(buffer.getChar());
                //fileOutputStream.write(buffer.get());
                byteArrayOutputStream.write(buffer.get());
            }

            // WARNING: the above loop is evil.
            // See comments in superclass.
            buffer.clear(); // Empty buffer
        }

        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.close();

        byte bytes[] = byteArrayOutputStream.toByteArray();

        if (bytes.length > 0) {
            // Ok get the prevayler object, save the data to it..
            PrevaylerPersister prevaylerPersister = PrevaylerPersister.getInstance();
            Prevayler prevayler = prevaylerPersister.getPrevayler();

            RantSystem rantSystem = (RantSystem) prevayler.prevalentSystem();
            PerformanceMetricCreateTransaction transaction = new PerformanceMetricCreateTransaction(rantSystem.nextPerformanceMetricId(), new String(bytes), "Tull_Method", "192.168.0.1", (long) 0, (long) 1, (long) 1);

            try {
                PerformanceMetric performanceMetric = (PerformanceMetric) transaction.executeUsing(prevayler);
                System.out.println("classname = " + performanceMetric.getClassName());
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }

        if (count < 0) {
            // Close channel on EOF; invalidates the key
            channel.close();
            return;
        }

        // Resume interest in OP_READ
        key.interestOps(key.interestOps() | SelectionKey.OP_READ);

        // Cycle the selector so this key is active again
        key.selector().wakeup();

        // Sleep this thread
        this.wait();
    }
}