package org.oslo.aspects;

import org.oslo.producer.DataProducer;
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
    MethodJoinPoint jp = (MethodJoinPoint)joinPoint;

    DataProducer dataProducer = DataProducer.getInstance();
    dataProducer.logJointPointEntry(jp);

    final Object result = joinPoint.proceed();

    dataProducer.logJointPointExit(jp);
    return result;
  }
}
