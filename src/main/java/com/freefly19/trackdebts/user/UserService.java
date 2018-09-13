package com.freefly19.trackdebts.user;

import com.freefly19.trackdebts.security.UserRequestContext;
import com.spencerwi.either.Either;

import java.util.List;

public interface UserService {
    Either<String, User> registerUser(RegisterUserCommand command);
    Either<String, String> obtainToken(ObtainTokenCommand command);
    List<UserDto> findAll();
    List<UserBalanceDto> getBalance(UserRequestContext context);
}
