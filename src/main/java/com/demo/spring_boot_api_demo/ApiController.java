package com.demo.spring_boot_api_demo;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "message", "Hello! This is a deployed Spring Boot app.",
                "endpoints", new String[]{"/health", "/time", "/echo?msg=hi"}
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

    @GetMapping("/time")
    public Map<String, String> time() {
        return Map.of("server_time", OffsetDateTime.now().toString());
    }

    @GetMapping("/echo")
    public Map<String, String> echo(@RequestParam(defaultValue = "hello") String msg) {
        return Map.of("echo", msg);
    }

    @GetMapping("/protected")
    public Map<String, String> protectedEndpoint() {
        return Map.of("message", "You are authenticated!");
    }
}