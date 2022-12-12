package me.arrhioui.aspect;

public aspect LogAspect {

    pointcut pc1() : execution(* me.arrhioui.service.*.*(..));

//    before() : pc1(){
//        System.out.println("Hello 1");
//    }
//
//    after() : pc1(){
//        System.out.println("Hello 2");
//    }
}
