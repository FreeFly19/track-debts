package com.freefly19.trackdebts.user;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ObtainTokenCommand {
    private String email;
    private String password;
}
