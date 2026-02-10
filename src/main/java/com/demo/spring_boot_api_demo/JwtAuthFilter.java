package com.demo.spring_boot_api_demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7).trim();

        try {
            Claims claims = jwtService.parseAndValidate(token);

            if (!"access_token".equals(claims.get("token_type"))) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Object rolesObj = claims.get("roles");
            List<String> roles = new ArrayList<>();

            if (rolesObj instanceof List<?> list) {
                for (Object r : list) {
                    roles.add(r.toString());
                }
            }

            var authorities = roles.stream()
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                    .toList();

            var auth = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), null, authorities
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}