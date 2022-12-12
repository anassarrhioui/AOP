package me.arrhioui.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Scanner;

@Aspect
public class SecurityAspect {
    private final String username = "root";
    private final String password = "123";

    @Around(value = "execution(* me.arrhioui.Application.start(..))", argNames = "pjp,joinPoint")
    public Object secureApp(ProceedingJoinPoint pjp, JoinPoint joinPoint) throws Throwable {
        Object proceed = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Authentication");
        System.out.print("Username :");
        String username = scanner.next();
        System.out.print("Password :");
        String pass = scanner.next();
        if (username.equals("root") && pass.equals("123")) {
            System.out.println("Before starting");
            proceed = pjp.proceed();
            System.out.println("End of Application");
        } else {
            System.out.println("Access denied ...");
        }

        return proceed;
    }
}
