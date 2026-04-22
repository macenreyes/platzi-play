package com.platzi.play.web.controller;

import com.platzi.play.domain.service.PlatziPlayAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private final String plataform;
    private final PlatziPlayAIService service;

    public HelloController(@Value("${spring.application.name}") String plataform, PlatziPlayAIService service) {
        this.plataform = plataform;
        this.service = service;
    }
    @GetMapping("/hello")
    public String hello(){
        return this.service.generateGreeting(plataform);
    }
}
