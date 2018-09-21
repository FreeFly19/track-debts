package com.freefly19.trackdebts.user;

import lombok.Value;

@Value
public class TokenDto {
    private final Long id;
    private final String email;
    private final String token;
}
