package com.freefly19.trackdebts.user;

import lombok.Value;

@Value
public class ObtainTokenCommand {
    private String email;
    private String password;
}
