package com.freefly19.trackdebts.user;

import com.spencerwi.either.Either;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        String token = Jwts.builder()
                .claim("id", 7)
                .claim("email", command.getEmail())
                .compact();

        return Either.right(token);
    }
}
