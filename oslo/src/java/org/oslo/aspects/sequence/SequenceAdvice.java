package org.oslo.aspects.sequence;

import org.codehaus.aspectwerkz.advice.PreAdvice;
import org.codehaus.aspectwerkz.joinpoint.JoinPoint;
import org.codehaus.aspectwerkz.joinpoint.MethodJoinPoint;
import org.codehaus.aspectwerkz.joinpoint.CallerSideJoinPoint;
import org.oslo.producer.MetricLogger;
import org.oslo.metrics.sequence.SequenceMetric;
import org.oslo.plugins.sequence.SequencePlugin;

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
public class SequenceAdvice extends PreAdvice {
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

    public void execute(JoinPoint joinPoint) throws Throwable {
        CallerSideJoinPoint jp = (CallerSideJoinPoint) joinPoint;

        // Write out the information
        //String outString = "[MethodClassName] = " + jp.getMethodClassName() + " [MethodName] = " + jp.getMethodName() + " [TargetClass] = " + jp.getTargetClass() + " [Signature] = " + jp.getSignature() + "\n";
        SequenceMetric sequenceMetric = new SequenceMetric(jp.getMethodName(), jp.getMethodClassName(), jp.getTargetClass().getName(), "");
        sequenceMetric.setPluginName(SequencePlugin.class.getName());
        dataProducer.logMetric(sequenceMetric);

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
    }

}
