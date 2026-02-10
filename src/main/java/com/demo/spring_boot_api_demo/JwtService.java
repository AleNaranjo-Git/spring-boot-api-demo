package com.demo.spring_boot_api_demo;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String issuer;
    private final SecretKey key;

    public JwtService(
            @Value("${demo.jwt.issuer}") String issuer,
            @Value("${demo.jwt.secret}") String secret
    ) {
        this.issuer = issuer;
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String issueIdToken(String username, List<String> groups) {
        Instant now = Instant.now();

        return Jwts.builder()
                .issuer(issuer)
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(3600)))
                .claim("token_type", "id_token")
                .claim("groups", groups)
                .signWith(key)
                .compact();
    }

    public String issueAccessToken(String username, List<String> roles) {
        Instant now = Instant.now();

        return Jwts.builder()
                .issuer(issuer)
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(3600)))
                .claim("token_type", "access_token")
                .claim("roles", roles)
                .signWith(key)
                .compact();
    }

    public Claims parseAndValidate(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}