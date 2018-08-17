package com.freefly19.trackdebts.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    @PostMapping("/users")
    UserDto register(@RequestBody @Valid RegisterUserCommand command) {
        return new UserDto(1, command.getEmail());
    }

}
