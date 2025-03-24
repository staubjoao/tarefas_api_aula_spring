package com.uem.tarefasAPI.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/hello")
@CrossOrigin(origins = "*")
public class HelloWordController {
    

    @GetMapping
    public String getHelloWord() {
        return "Hello Word!";
    }
    
}