package org.oslo.aspects.sequence;

import org.codehaus.aspectwerkz.advice.PreAdvice;
import org.codehaus.aspectwerkz.advice.AroundAdvice;
import org.codehaus.aspectwerkz.joinpoint.JoinPoint;
import org.codehaus.aspectwerkz.joinpoint.MethodJoinPoint;
import org.codehaus.aspectwerkz.joinpoint.CallerSideJoinPoint;
import org.oslo.producer.MetricLogger;
import org.oslo.metrics.sequence.SequenceMetric;
import org.oslo.plugins.sequence.SequencePlugin;
import org.oslo.aspects.introduction.RantProcessIDIntroductionImpl;
import org.oslo.aspects.introduction.RantProcessIDIntroduction;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 14, 2003
 * Time: 11:43:47 PM
 * To change this template use Options | File Templates.
 */
public class SequenceAdvice extends AroundAdvice {
    FileOutputStream fileOutputStream;
    MetricLogger dataProducer = MetricLogger.getInstance();

    public SequenceAdvice() {
        super();

        // Create a new Plugin and register it
        dataProducer.registerPlugin(new SequencePlugin());

        try {
            fileOutputStream = new FileOutputStream("PreAdvice.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    public Object execute(JoinPoint joinPoint) throws Throwable {
        MethodJoinPoint jp = (MethodJoinPoint) joinPoint;

        // Write out the information
        //String outString = "[MethodClassName] = " + jp.getMethodClassName() + " [MethodName] = " + jp.getMethodName() + " [TargetClass] = " + jp.getTargetClass() + " [Signature] = " + jp.getSignature() + "\n";

        // Ok we want to
        Object targetObject = jp.getTargetObject();

        /*if(targetObject instanceof RantProcessIDIntroductionImpl) {
            RantProcessIDIntroduction rantProcessIDIntroduction = (RantProcessIDIntroduction)targetObject;

            // Is this a new sequence ? then set an ID number and signal that we are the first one doing this
            if(rantProcessIDIntroduction.getRantProcessID() == null) {
                rantProcessIDIntroduction.setRantProcessID(Long.toString(System.currentTimeMillis()));
                //rantProcessIDIntroduction.
            }
        } */




        /*SequenceMetric sequenceMetric = new SequenceMetric("METHOD:" + jp.getTargetClass().getName(), jp.getMethodName());
        sequenceMetric.setPluginName(SequencePlugin.class.getName());

        dataProducer.logMetric(sequenceMetric); */

        /*System.out.print("METHOD: [getMethod().getDeclaringClass().getName()] = " + jp.getMethod().getDeclaringClass().getName());
        System.out.print(" [getMethodName()] = " + jp.getMethodName());
        System.out.println(" [getTargetClass()] = " + jp.getTargetClass().getName());*/
//        System.out.println(" getResult().toString() = " + jp.getResult().toString());


        // List all the types of the method
        /*Class classes[] = jp.getParameterTypes();

        for (int i = 0; i < classes.length; i++) {
            Class aClass = classes[i];
            System.out.println("ParameterType: " + aClass);
        }

        // Methods
        String strings[] = jp.getParameterTypeNames();

        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            System.out.println("ParameterTypeName: " + string);
        } */

        final Object result = joinPoint.proceed();

        /*sequenceMetric = new SequenceMetric(jp.getTargetClass().getName(), jp.getMethodName());
        sequenceMetric.setPluginName(SequencePlugin.class.getName());

        dataProducer.logMetric(sequenceMetric); */


        return result;
    }

}
