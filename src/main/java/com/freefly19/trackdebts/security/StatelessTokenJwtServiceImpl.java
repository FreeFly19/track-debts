package com.freefly19.trackdebts.security;

import com.freefly19.trackdebts.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatelessTokenJwtServiceImpl implements StatelessTokenService {
    private final String secret;

    public String createToken(User user) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secret)
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .compact();
    }
}
