package com.freefly19.trackdebts.user;

import com.freefly19.trackdebts.security.UserRequestContext;
import com.spencerwi.either.Either;

import java.util.List;

public interface UserService {
    Either<String, User> registerUser(RegisterUserCommand command);
    Either<String, TokenDto> obtainToken(ObtainTokenCommand command);
    List<UserDto> findAll();
    UserExtendedDto getById(long id);
    UserExtendedDto update(UpdateUserInfoCommand cmd, UserRequestContext context);
}
