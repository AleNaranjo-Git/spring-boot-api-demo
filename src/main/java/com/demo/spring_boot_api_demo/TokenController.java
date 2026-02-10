package com.demo.spring_boot_api_demo;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
public class TokenController {

    private final JwtService jwtService;

    public TokenController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public record TokenResponse(String id_token, String access_token) {}

    @PostMapping("/token")
    public TokenResponse tokenPost(@RequestBody(required = false) String body) {
        return issue("userA");
    }

    @GetMapping("/token")
    public TokenResponse tokenGet(@RequestParam(defaultValue = "userA") String username) {
        return issue(username);
    }

    private TokenResponse issue(String username) {
        List<String> roles;

        if ("userB".equalsIgnoreCase(username)) {
            roles = List.of("USER", "ADMIN");
        } else {
            roles = List.of("USER");
        }

        return new TokenResponse(
                jwtService.issueIdToken(username, roles),
                jwtService.issueAccessToken(username, roles)
        );
    }
}