/**
 * Created by IntelliJ IDEA.
 * User: ac08683
 * Date: 29.apr.2003
 * Time: 13:11:03
 * To change this template use Options | File Templates.
 */
package org.oslo.producer;

import aspectwerkz.joinpoint.MethodJoinPoint;

import java.io.*;
import java.net.Socket;

public class DataProducer {
    private static DataProducer ourInstance;
    private static DataOutputStream outSequence;
    private static Socket socket;
    private static BufferedOutputStream bufferedOutputStream;

    public synchronized static DataProducer getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataProducer();
        }
        return ourInstance;
    }

    private DataProducer() {

        try {
            //Prepare socket, one instance only
            socket = new Socket("127.0.0.1", 8010);
            //Create outputStream
            bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

            //outSequence = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("sequence.txt")));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    public void logJointPointEntry(MethodJoinPoint methodJoinPoint) {
        String outString = "--> " + methodJoinPoint.getTargetClass().getName() + "::" + methodJoinPoint.getMethodName() + "\n";
        //System.out.println(outString);

        // Get full class name
        String className = methodJoinPoint.getTargetClass().getName();
        outString = "(" + className.substring(className.lastIndexOf(".") + 1) + " " + methodJoinPoint.getMethodName() + "\n";

        try {
            bufferedOutputStream.write(outString.getBytes());
            bufferedOutputStream.flush();
            //outSequence.write(outString.getBytes());
            //outSequence.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    public void logJointPointExit(MethodJoinPoint methodJoinPoint) {
        String outString = "<-- " + methodJoinPoint.getTargetClass().getName() + "::" + methodJoinPoint.getMethodName() + "\n";
        //System.out.println(outString);

        outString = ")";

        try {
            bufferedOutputStream.write(outString.getBytes());
            bufferedOutputStream.flush();
            //outSequence.write(outString.getBytes());
            //outSequence.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    public void finalize() {
        try {
            socket.close();
            //outSequence.flush();
            //outSequence.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }
}

