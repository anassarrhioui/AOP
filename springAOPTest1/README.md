
## ServiceImpl
```java
@Service
public class ServiceImpl implements IService {
    @Override
    public void process() {
        System.out.println("Process");
    }

    @Override
    @Timer
    @SecuredBy(roles = {"USER"})
    public double compute() {
        System.out.println("Compute");
        return Math.random()*4000;
    }
}

```

## Timer
Pour les méthodes annotées par @Timer on va leur calculer le temps d'exécution

```java
public @interface Timer { }
```


## LogAspect
```java

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

    @Around("@annotation(me.arrhioui.springaoptest1.aspect.Timer)")
    public Object timerAdvice(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        long end = System.currentTimeMillis();
        log.info(pjp.getSignature() + " took " + (end - start) + " ms");
        return proceed;
    }
    
    
}

```


## SecuredBy
Chaque méthode contenant l’annotation SecuredBy est sécurisée et pour pouvoir l'exécuter on doit obligatoirement avoir les rôles adéquats

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SecuredBy {
    String[] roles();
}
```


## ServiceImpl
Les rôles sont récupérés à partir de la requête HTTP dans l’URL
```java
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@AllArgsConstructor
public class SecurityAspect {
    private HttpServletRequest request;

    @Around(value = "@annotation(securedBy)", argNames = "pjp,securedBy")
    public Object securityByRolesAdvice(ProceedingJoinPoint pjp, SecuredBy securedBy) throws Throwable {
        System.out.println("request = " + request.getServletPath());
        String rolesParam = request.getParameter("roles");
        if(rolesParam == null)
            throw new RuntimeException("Access Denied");
        String [] userRoles = rolesParam.split(",");
        String[] roles = securedBy.roles();
        boolean authorized = false;
        for (String role : roles) {
            for (String userRole : userRoles){
                if(role.equals(userRole)){
                    authorized = true;
                    break;
                }
            }
        }

        if (authorized)
            return pjp.proceed();

        throw new RuntimeException("Access Denied");

    }
}

```


## TestRestController
```java
@RestController
@RequestMapping("/testAOP")
@AllArgsConstructor
public class TestRestController {

    final private IService service;

    @GetMapping("/process")
    public String process(){
        service.process();
        return "Done";
    }

    @GetMapping("/compute")
    public double compute(){
        return service.compute();
    }
}
```
