package com.swordmaster.excalibur;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebRESTController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, SwordMaster!";
    }
}
