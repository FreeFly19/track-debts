package com.freefly19.trackdebts.user;

import com.freefly19.trackdebts.security.StatelessTokenService;
import com.spencerwi.either.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StatelessTokenService tokenService;

    @Override
    public Either<String, User> registerUser(RegisterUserCommand command) {
        return userRepository.findOne(Example.of(User.builder().email(command.getEmail()).build()))
                .map(u -> Either.<String, User>left("User with " + command.getEmail() + " email already exists"))
                .orElseGet(() ->
                        Either.right(
                                userRepository.save(
                                        User.builder()
                                                .email(command.getEmail())
                                                .password(passwordEncoder.encode(command.getPassword()))
                                                .build())));
    }

    @Override
    public Either<String, String> obtainToken(ObtainTokenCommand command) {
        return userRepository.findOne(Example.of(User.builder().email(command.getEmail()).build()))
                .filter(u -> passwordEncoder.matches(command.getPassword(), u.getPassword()))
                .map(u -> Either.<String, String>right(tokenService.createToken(u)))
                .orElseGet(() -> Either.left("Bad credentials"));
    }

}
