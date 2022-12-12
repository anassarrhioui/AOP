package me.arrhioui.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.logging.Logger;

@Aspect
public class LoggingAspect {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Pointcut("execution(* me.arrhioui.service.*.*(..))")
    public void pointCut1() {
    }

//    @Before("pointCut1()")
//    public void beforePC1(JoinPoint joinPoint){
//        System.out.println("Before Method execution " + joinPoint.getSignature());
//    }
//
//    @After("pointCut1()")
//    public void afterPC1(JoinPoint joinPoint){
//        System.out.println("After Method execution " + joinPoint.getSignature());
//    }

    @Around(value = "pointCut1()", argNames = "pjp,joinPoint")
    public Object aroundPointCut1(ProceedingJoinPoint pjp, JoinPoint joinPoint) throws Throwable {
        logger.info("Execution of : " + joinPoint.getSignature());
        long start = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        long end = System.currentTimeMillis();
        logger.info("End of Execution of " + joinPoint.getSignature() + ", duration : " + (end-start));
        return proceed;
    }

}
