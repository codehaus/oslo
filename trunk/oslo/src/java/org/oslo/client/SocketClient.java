package org.oslo.client;

import java.net.Socket;
import java.net.InetAddress;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 5, 2003
 * Time: 2:54:24 PM
 * To change this template use Options | File Templates.
 */
public class SocketClient {
    public SocketClient() {
    }

    public static void main(String[] args) {
        new SocketClient().startClient();
    }

    public void startClient() {
        int token;
        StringBuffer stringBuffer = new StringBuffer();

        try {
            Socket socket = new Socket("127.0.0.1", 8010);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

            bufferedOutputStream.write("Hello I'm the client\n".getBytes());
            bufferedOutputStream.flush();

            while((token = bufferedInputStream.read()) != -1) {
                char ch = (char)token;
                System.out.print(ch);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        //System.out.println(stringBuffer.toString());
    }
}
