package me.arrhioui.springaoptest1.web;

import lombok.AllArgsConstructor;
import me.arrhioui.springaoptest1.service.IService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
