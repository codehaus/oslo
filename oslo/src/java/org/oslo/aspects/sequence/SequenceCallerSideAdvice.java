package org.oslo.aspects.sequence;

import org.codehaus.aspectwerkz.advice.PreAdvice;
import org.codehaus.aspectwerkz.advice.PostAdvice;
import org.codehaus.aspectwerkz.joinpoint.JoinPoint;
import org.codehaus.aspectwerkz.joinpoint.CallerSideJoinPoint;
import org.oslo.producer.MetricLogger;
import org.oslo.plugins.sequence.SequencePlugin;
import org.oslo.metrics.sequence.SequenceMetric;

import java.util.ArrayList;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jun 3, 2003
 * Time: 9:22:33 PM
 * To change this template use Options | File Templates.
 */
public class SequenceCallerSideAdvice extends PreAdvice {
    MetricLogger dataProducer = MetricLogger.getInstance();

    public SequenceCallerSideAdvice() {
        dataProducer.registerPlugin(new SequencePlugin());
    }

    public void execute(JoinPoint joinPoint) throws Throwable {
        CallerSideJoinPoint jp = (CallerSideJoinPoint)joinPoint;

        // Generate Parameter list of types and names
        String parameterNames = jp.getCalleeMethodSignature();
        Class[] parameterTypes = jp.getCalleeMethodParameterTypes();

        ArrayList parameters = new ArrayList();

        for (int i = 0; i < parameterTypes.length; i++) {
            Class parameterType = parameterTypes[i];
            //String parameterName = parameterNames[i];

            //String[] paramterObject = new String[2];
            //paramterObject[0] = parameterType.getName();
            //paramterObject[1] = "";
            parameters.add(parameterType.getName());
        }

        System.out.println(jp.getCalleeMethodSignature());

        SequenceMetric sequenceMetric = new SequenceMetric(Long.toString(System.currentTimeMillis()), jp.getCallerClassName(), jp.getCallerMethodName(), jp.getCalleeClassName(), jp.getCalleeMethodName(), jp.getCalleeMethodReturnTypeName(), parameters);
        sequenceMetric.setPluginName(SequencePlugin.class.getName());
        dataProducer.logMetric(sequenceMetric);

        /*Class targetClass = jp.getTargetClass();
        Method[] methods = targetClass.getMethods();
        Method selectedmetod = null;

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if(method.getName().equals(jp.getCalleeMethodName())) {
                selectedmetod = method;
                break;
            }
        } */

        /*System.out.print("CALLER: [getCallerClassName()] = " + jp.getCallerClassName());
        System.out.print(" [getCallerMethodName()] = " + jp.getCallerMethodName());
        System.out.print(" [getCalleeClassName()] = " + jp.getCalleeClassName());
        System.out.println(" [getCalleeMethodName()] = " + jp.getCalleeMethodName());*/
        //System.out.println(" getTargetObject().toString() = " + jp.getTargetObject().toString());
        final Object result = jp.proceed();
    }
}
