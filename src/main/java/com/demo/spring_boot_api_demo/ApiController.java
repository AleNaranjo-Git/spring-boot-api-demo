package com.demo.spring_boot_api_demo;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
            "message", "Spring Boot OIDC-claims authorization demo",
            "public_endpoints", new String[]{"/health", "/token","/token?username=userB", "/demo"},
            "protected_endpoints", new String[]{"/resource1", "/resource2"}
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}