package org.oslo.aspects.sequence;

import org.codehaus.aspectwerkz.advice.PreAdvice;
import org.codehaus.aspectwerkz.joinpoint.JoinPoint;
import org.codehaus.aspectwerkz.joinpoint.CallerSideJoinPoint;
import org.oslo.producer.MetricLogger;
import org.oslo.plugins.sequence.SequencePlugin;
import org.oslo.metrics.sequence.SequenceMetric;

import java.util.ArrayList;

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
        Class[] parameterTypes = jp.getCalleeMethodParameterTypes();

        ArrayList parameters = new ArrayList();

        for (int i = 0; i < parameterTypes.length; i++) {
            Class parameterType = parameterTypes[i];
            parameters.add(parameterType.getName());
        }

        SequenceMetric sequenceMetric = new SequenceMetric(Long.toString(System.currentTimeMillis()), jp.getCallerClassName(), jp.getCallerMethodName(), jp.getCalleeClassName(), jp.getCalleeMethodName(), jp.getCalleeMethodReturnTypeName(), parameters);
        sequenceMetric.setPluginName(SequencePlugin.class.getName());
        dataProducer.logMetric(sequenceMetric);
        final Object result = jp.proceed();
    }
}
