package com.Search_Engine.Search_engine.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeathCheck {
    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok" ;
    }

}
