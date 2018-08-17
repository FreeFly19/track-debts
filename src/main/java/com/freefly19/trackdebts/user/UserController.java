package com.freefly19.trackdebts.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    UserDto register(@RequestBody @Valid RegisterUserCommand command) {
        return new UserDto(userService.registerUser(command));
    }
}
