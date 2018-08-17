package com.freefly19.trackdebts.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @PostMapping("/users")
    UserDto register(@RequestBody RegisterUserCommand command) {
        return new UserDto(1, command.getEmail());
    }

}
