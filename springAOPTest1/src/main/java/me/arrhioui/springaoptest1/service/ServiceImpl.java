package me.arrhioui.springaoptest1.service;

import me.arrhioui.springaoptest1.aspact.SecuredBy;
import me.arrhioui.springaoptest1.aspact.Timer;
import org.springframework.stereotype.Service;

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
