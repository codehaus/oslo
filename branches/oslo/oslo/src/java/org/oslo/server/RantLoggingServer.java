package org.oslo.server;

import org.oslo.server.thread.WorkerThread;
import org.oslo.server.thread.ThreadPool;

import java.nio.channels.*;
import java.nio.ByteBuffer;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 5, 2003
 * Time: 1:24:28 PM
 * To change this template use Options | File Templates.
 */
public class RantLoggingServer {
    private static int socketPort = 8010;

    private static final int MAX_THREADS = 5;
    private ThreadPool pool = new ThreadPool(MAX_THREADS);
    // Use the same byte buffer for all channels. A single thread is
    // servicing all the channels, so no danger of concurrent acccess.
    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    public RantLoggingServer() {
    }

    public static void main(String[] args) throws Exception {
        new RantLoggingServer().start();
    }

    public void start() throws Exception {
        System.out.println("Listening on port: " + socketPort);

        //Allocate an unbound server socket channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //Get the associated  ServerSocket to bind it with
        ServerSocket serverSocket = serverSocketChannel.socket();

        //Create a Selector
        Selector selector = Selector.open();

        //Set the socket port that the server will listen on
        serverSocket.bind(new InetSocketAddress(socketPort));

        //Set nonblocking mode for the listening socket
        serverSocketChannel.configureBlocking(false);

        //Register the ServerSocketChannel with the Selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //Block until there is something available
            int n = selector.select();

            if (n == 0) {
                continue;   //Nothing to do
            }

            Iterator it = selector.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) it.next();

                //Is this a new connection comming in ?
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel channel = server.accept();

                    registerChannel(selector, channel, SelectionKey.OP_READ);

                    sayHello(channel);
                }

                // Is there data to read on this channel?
                if (selectionKey.isReadable()) {
                    readDataFromSocket(selectionKey);
                }

                // Remove key from selected set; it's been handled
                it.remove();
            }
        }
    }

    /**
     * Register the given channel with the given selector for
     * the given operations of interest
     */
    protected void registerChannel(Selector selector, SelectableChannel channel, int ops) throws Exception {
        if (channel == null) {
            return; // could happen
        }
        // Set the new channel nonblocking
        channel.configureBlocking(false);
        // Register it with the selector
        channel.register(selector, ops);
    }

    /**
     * Spew a greeting to the incoming client connection.
     * @param channel The newly connected SocketChannel to say hello to.
     */
    private void sayHello(SocketChannel channel) throws Exception {
        buffer.clear();
        buffer.put("Hi there!\r\n".getBytes());
        buffer.flip();
        channel.write(buffer);
    }

    protected void readDataFromSocket(SelectionKey key) throws Exception {
        WorkerThread worker = pool.getWorker();

        if (worker == null) {
            // No threads available. Do nothing. The selection
            // loop will keep calling this method until a
            // thread becomes available. This design could
            // be improved.
            return;
        }

        // Invoking this wakes up the worker thread, then returns
        worker.serviceChannel(key);
    }



}

