package com.freefly19.trackdebts.user;

import lombok.Data;

@Data
class RegisterUserCommand {
    private String email;
    private String password;
}
