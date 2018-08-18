package com.freefly19.trackdebts.user;

import com.freefly19.trackdebts.AppError;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/users")
    ResponseEntity<?> register(@RequestBody @Valid RegisterUserCommand command) {
        return userService
                .registerUser(command)
                .map(AppError::new, UserDto::new)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }

    @PostMapping(value = "/users/token")
    ResponseEntity<?> token(@RequestBody ObtainTokenCommand command) {
        return userService.obtainToken(command)
                .map(AppError::new, TokenDto::new)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }
}
