package com.freefly19.trackdebts.user;

import com.spencerwi.either.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Either<String, User> registerUser(RegisterUserCommand command) {
        return userRepository.findOne(Example.of(User.builder().email(command.getEmail()).build()))
                .map(u -> Either.<String, User>left("User with " + command.getEmail() + " email already exists"))
                .orElseGet(() ->
                        Either.right(userRepository.save(
                                User.builder().email(command.getEmail()).password(command.getPassword()).build())));
    }
}
