package org.oslo.aspects;

import org.oslo.producer.MetricLogger;
import org.oslo.aspects.introduction.RantProcessIDIntroduction;
import org.codehaus.aspectwerkz.joinpoint.JoinPoint;
import org.codehaus.aspectwerkz.joinpoint.MethodJoinPoint;
import org.codehaus.aspectwerkz.advice.AroundAdvice;

/**
 * Created by IntelliJ IDEA.
 * User: ac08683
 * Date: 29.apr.2003
 * Time: 13:49:24
 * To change this template use Options | File Templates.
 */
public class LoggingAdvice extends AroundAdvice {
    public LoggingAdvice() {
        super();
    }

    public Object execute(JoinPoint joinPoint) throws Throwable {
        MethodJoinPoint jp = (MethodJoinPoint) joinPoint;

        //MetricLogger dataProducer = MetricLogger.getInstance();
        ///dataProducer.logJointPointEntry(jp);
        System.out.println("Hello");

        Object[] objects = jp.getParameters();

        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];

            // Check if this contains the actual Interface
            if(object instanceof RantProcessIDIntroduction)
                System.out.println("++ Enter Object: " + object.toString());
            else
                System.out.println("Enter Object: " + object.toString());
        }

        Class[] classes = jp.getParameterTypes();

        for (int i = 0; i < classes.length; i++) {
            Class aClass = classes[i];
            System.out.println("Class name: " + aClass.getName());
        }

        // Execute the joinpoint
        final Object result = joinPoint.proceed();

        //dataProducer.logJointPointExit(jp);
        return result;
    }
}
