package com.freefly19.trackdebts.security;

import com.freefly19.trackdebts.user.User;
import io.jsonwebtoken.*;

public class StatelessTokenJwtServiceImpl implements StatelessTokenService {
    private final JwtParser parser;
    private final JwtBuilder builder;

    public StatelessTokenJwtServiceImpl(String secret) {
        parser = Jwts.parser().setSigningKey(secret);
        builder = Jwts.builder().signWith(SignatureAlgorithm.HS512, secret);
    }

    public String createToken(User user) {
        return builder
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .compact();
    }

    @Override
    public TokenClaim parseUserData(String token) {
        // TODO: add expiration date checker!!! [Security]
        Claims claims = parser.parseClaimsJws(token).getBody();

        return TokenClaim
                .builder()
                .id(claims.get("id", Long.class))
                .email(claims.get("email", String.class))
                .build();
    }
}
