package org.oslo.client.aspects.performance;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jul 22, 2003
 * Time: 2:13:54 PM
 * To change this template use Options | File Templates.
 */
import org.codehaus.aspectwerkz.advice.AroundAdvice;
import org.codehaus.aspectwerkz.joinpoint.JoinPoint;
import org.codehaus.aspectwerkz.joinpoint.MethodJoinPoint;
import org.oslo.plugins.performanceplugin.metric.PerformanceMetric;
import org.oslo.plugins.performanceplugin.plugin.PerformancePlugin;
import org.oslo.client.producer.MetricLogger;

import java.net.InetAddress;

public class MethodPerformanceAdvice extends AroundAdvice {
    MetricLogger dataProducer = MetricLogger.getInstance();

    public MethodPerformanceAdvice() {
        dataProducer.registerPlugin(new PerformancePlugin());
    }

    public Object execute(JoinPoint joinPoint) throws Throwable {
        // Throw the joingpoint to its correct type
        MethodJoinPoint methodJointPoint = (MethodJoinPoint)joinPoint;

        System.out.println("Triggered MethodPerformanceAdvice");

        // Take the time for the method being executed.
        long currentTime = System.currentTimeMillis();

        Object result = methodJointPoint.proceed();

        long afterMethod = System.currentTimeMillis();

        String key = Long.toString(System.currentTimeMillis());
        String className = methodJointPoint.getTargetClass().getName();
        String methodName = methodJointPoint.getMethodName();
        String ip = InetAddress.getLocalHost().getHostAddress();
        long date = System.currentTimeMillis();

        PerformanceMetric performanceMetric = new PerformanceMetric(key, className, methodName, ip, date , currentTime, afterMethod);
        performanceMetric.setPluginName(PerformancePlugin.class.getName());

        dataProducer.logMetric(performanceMetric);
        //System.out.println("Class: " + methodJointPoint.getTargetClass().getName() + ", Method: " + methodJointPoint.getMethodName() + ", Time: " + (afterMethod - currentTime) + " msec");
        return result;  //To change body of implemented methods use Options | File Templates.
    }
}
