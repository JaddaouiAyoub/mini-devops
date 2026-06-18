package com.example.mini_devops.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Value("${APP_NAME:DefaultName}")
    private String appName;

    @GetMapping("/hello")
    public String hello(){
        return "Helloo devops , j'utilisa maintenant docker compose : " + appName;
    }
}
