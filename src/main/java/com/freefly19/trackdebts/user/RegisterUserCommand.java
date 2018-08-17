package com.freefly19.trackdebts.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
class RegisterUserCommand {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
