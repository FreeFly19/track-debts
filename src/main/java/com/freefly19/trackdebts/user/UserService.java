package com.freefly19.trackdebts.user;

import com.spencerwi.either.Either;

public interface UserService {
    Either<String, User> registerUser(RegisterUserCommand command);
    Either<String, String> obtainToken(ObtainTokenCommand command);
}
