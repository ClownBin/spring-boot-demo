package com.example.demo.annotation.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TrackTimeAop {
    private static Logger logger =LoggerFactory.getLogger(TrackTimeAop.class);

    @Pointcut("@annotation(TrackTime)")
    private void trackTime(){}

    @Around("trackTime()")
    public Object calTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        long endTime;
        try {
            logger.info("Around begin" + startTime);
            Object result = joinPoint.proceed();
            endTime = System.currentTimeMillis();
            logger.info("Around end" + endTime);
            return result;
        } catch (Throwable e){
            endTime = System.currentTimeMillis();
            logger.error("Around " + joinPoint + "used " + (endTime - startTime) + " ms with exception :",e);
            throw e;
        }
    }
}
