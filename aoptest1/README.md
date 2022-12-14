
## Entity Compte

```java
package me.arrhioui.entity;

public class Compte {

    public Compte() {
    }

    public Compte(Long code, double solde) {
        this.code = code;
        this.solde = solde;
    }

    private Long code;
    private double solde;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "code=" + code +
                ", solde=" + solde +
                '}';
    }
}
```

## MetierImpl
```java
package me.arrhioui.service;

import me.arrhioui.entity.Compte;

import java.util.HashMap;
import java.util.Map;

public class MetierImpl implements IMetier {
    private Map<Long, Compte> comptes=new HashMap<>();
    @Override
    public void addCompte(Compte cp) {
        comptes.put(cp.getCode(),cp);
    }
    @Override
    public void verser(Long code, double mt) {
        Compte cp=comptes.get(code);
        cp.setSolde(cp.getSolde()+mt);
    }
    @Override
    public void retirer(Long code, double mt) {
        Compte cp=comptes.get(code);
        cp.setSolde(cp.getSolde()-mt);
    }
    @Override
    public Compte getCompte(Long code) {
        return comptes.get(code);
    }
}

```
## LogAspect
Syntax AspectJ
```aspectj
public aspect LogAspect {

    pointcut pc1() : execution(* me.arrhioui.service.*.*(..));

    before() : pc1(){
        System.out.println("Hello 1");
    }

    after() : pc1(){
        System.out.println("Hello 2");
    }
}

```

## LoggingAspect
Syntax Annotation
```java

@Aspect
public class LoggingAspect {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Pointcut("execution(* me.arrhioui.service.*.*(..))")
    public void pointCut1() {
    }

    @Before("pointCut1()")
    public void beforePC1(JoinPoint joinPoint){
        System.out.println("Before Method execution " + joinPoint.getSignature());
    }

    @After("pointCut1()")
    public void afterPC1(JoinPoint joinPoint){
        System.out.println("After Method execution " + joinPoint.getSignature());
    }

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

```

## PatchAspect
```java
@Aspect
public class PatchAspect {
    @Around(value = "execution(* me.arrhioui.service.MetierImpl.retirer(..))&&args(code,mt)", argNames = "pjp,code,mt,joinPoint")
    public Object patch(ProceedingJoinPoint pjp, Long code, double mt, JoinPoint joinPoint) throws Throwable {
        System.out.println("Patch");
        MetierImpl metier=(MetierImpl) joinPoint.getTarget();
        Compte cp=metier.getCompte(code);
        if(cp.getSolde()>mt) {
            return pjp.proceed();
        }
        else
            throw new RuntimeException("Solde insuffisant");

    }
}

```

## SecurityAspect
```java
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
```
