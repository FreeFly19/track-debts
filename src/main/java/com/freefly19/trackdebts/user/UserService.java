package com.freefly19.trackdebts.user;

import com.spencerwi.either.Either;

import java.util.List;

public interface UserService {
    Either<String, User> registerUser(RegisterUserCommand command);
    Either<String, String> obtainToken(ObtainTokenCommand command);
    List<UserDto> findAll();
}
