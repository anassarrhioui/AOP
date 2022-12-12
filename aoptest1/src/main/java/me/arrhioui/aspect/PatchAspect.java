package me.arrhioui.aspect;

import me.arrhioui.entity.Compte;
import me.arrhioui.service.MetierImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

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
