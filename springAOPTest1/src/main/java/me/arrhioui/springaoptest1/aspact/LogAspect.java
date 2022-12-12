package me.arrhioui.springaoptest1.aspact;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log
@EnableAspectJAutoProxy
public class LogAspect {


    @Pointcut("execution(* me.arrhioui.springaoptest1.service.IService.process(..))")
    public void beforeProcessPC() {
    }

    @Before("beforeProcessPC()")
    public void beforeProcess(JoinPoint joinPoint) {
        log.info("Before Process");
    }

    @Around("@annotation(me.arrhioui.springaoptest1.aspact.Timer)")
    public Object timerAdvice(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        long end = System.currentTimeMillis();
        log.info(pjp.getSignature() + " took " + (end - start) + " ms");
        return proceed;
    }


}
