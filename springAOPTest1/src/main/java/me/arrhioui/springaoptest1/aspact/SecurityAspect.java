package me.arrhioui.springaoptest1.aspact;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

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
