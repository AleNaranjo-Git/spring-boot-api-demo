package com.demo.spring_boot_api_demo;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/resource1")
    public Map<String, String> resource1() {
        return Map.of("resource", "resource1");
    }

    @GetMapping("/resource2")
    public Map<String, String> resource2() {
        return Map.of("resource", "resource2");
    }
}