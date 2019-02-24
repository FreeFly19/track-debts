package com.freefly19.trackdebts.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterUserCommand {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 255)
    private String password;
}
