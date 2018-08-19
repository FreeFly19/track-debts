package com.freefly19.trackdebts.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freefly19.trackdebts.AppError;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AuthenticationFilter extends OncePerRequestFilter {
    private final StatelessTokenService tokenService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationFilter(StatelessTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");


        String prefix = "Bearer ";
        if(authorization == null || !authorization.startsWith(prefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(prefix.length());

        final TokenClaim tokenClaim;
        try {
            tokenClaim = tokenService.parseUserData(token);
        } catch (SignatureException e) {
            response.setStatus(403);
            objectMapper.writeValue(response.getOutputStream(), new AppError("Bearer Token is not valid"));
            response.flushBuffer();
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(tokenClaim.getEmail(), null, Collections.emptySet());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
