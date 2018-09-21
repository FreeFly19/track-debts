package com.freefly19.trackdebts.user;

import com.freefly19.trackdebts.moneytransaction.MoneyTransaction;
import com.freefly19.trackdebts.moneytransaction.MoneyTransactionRepository;
import com.freefly19.trackdebts.security.StatelessTokenService;
import com.freefly19.trackdebts.security.UserRequestContext;
import com.spencerwi.either.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StatelessTokenService tokenService;
    private final EntityManager entityManager;

    @Transactional
    @Override
    public Either<String, User> registerUser(RegisterUserCommand command) {
        entityManager.createNativeQuery("LOCK TABLE users IN EXCLUSIVE MODE").executeUpdate();
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

    @Transactional(readOnly = true)
    @Override
    public Either<String, TokenDto> obtainToken(ObtainTokenCommand command) {
        return userRepository.findOne(Example.of(User.builder().email(command.getEmail()).build()))
                .filter(u -> passwordEncoder.matches(command.getPassword(), u.getPassword()))
                .map(u -> Either.<String, TokenDto>right(new TokenDto(u.getId(), u.getEmail(), tokenService.createToken(u))))
                .orElseGet(() -> Either.left("Bad credentials"));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream().map(UserDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> oUser = userRepository.findOne(Example.of(User.builder().email(email).build()));

        User user = oUser
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), emptyList());
    }
}
