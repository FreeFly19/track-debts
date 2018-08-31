package com.freefly19.trackdebts.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freefly19.trackdebts.AppError;
import com.freefly19.trackdebts.util.DateTimeProvider;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final StatelessTokenService tokenService;
    private final DateTimeProvider dateTimeProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);


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

        UserRequestContext context = UserRequestContext.builder()
                .id(tokenClaim.getId())
                .email(tokenClaim.getEmail())
                .date(dateTimeProvider.now())
                .requestId(UUID.randomUUID().toString())
                .build();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(context, null, Collections.emptySet());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
