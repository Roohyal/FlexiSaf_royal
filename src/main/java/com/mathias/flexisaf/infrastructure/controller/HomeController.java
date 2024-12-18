package com.mathias.flexisaf.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {


    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }
    @GetMapping("/secured")
    public String secured() {
        return "Hello World!!!!, This is a secured page!";
    }

}
