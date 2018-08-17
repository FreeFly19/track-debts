package com.freefly19.trackdebts.user;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
class RegisterUserCommand {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 255)
    private String password;
}
