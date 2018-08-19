package com.freefly19.trackdebts.security;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class TokenClaim {
    private final long id;
    private final String email;
}
