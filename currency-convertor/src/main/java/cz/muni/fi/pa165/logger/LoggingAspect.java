package cz.muni.fi.pa165.logger;

import javax.inject.Named;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Named
@Aspect
public class LoggingAspect {

    @Around("execution(public * *(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        System.err.println("Calling method: "
                + joinPoint.getSignature());

        long start = System.nanoTime();
        Object result = joinPoint.proceed();
        long finish = System.nanoTime();

        System.err.println("Method finished: "
                + joinPoint.getSignature() + " Counted time in nanoseconds: " + (finish - start));



        return result;
    }

}
