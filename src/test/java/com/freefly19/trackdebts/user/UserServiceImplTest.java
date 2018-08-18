package com.freefly19.trackdebts.user;

import com.freefly19.trackdebts.security.StatelessTokenService;
import com.spencerwi.either.Either;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private StatelessTokenService tokenService;

    @Captor
    private ArgumentCaptor<User> userCaptor;


    @Before
    public void init() {
        userService = new UserServiceImpl(userRepository, passwordEncoder, tokenService);
    }


    @Test
    public void registerNewUserShouldPassSuccessfully() {
        when(userRepository.findOne(Example.of(User.builder().email("some@email.com").build())))
                .thenReturn(Optional.empty());

        when(userRepository.save(userCaptor.capture()))
                .then(ignore -> {
                    User savingUser = userCaptor.getValue();
                    savingUser.setId(13L);
                    return savingUser;
                });

        when(passwordEncoder.encode("not hashed yet")).thenReturn("hashed");

        Either<String, User> registration = userService.registerUser(RegisterUserCommand.builder()
                .email("some@email.com")
                .password("not hashed yet").build());

        Assert.assertTrue(registration.isRight());
        Assert.assertEquals(13L, registration.getRight().getId().longValue());
        Assert.assertEquals("some@email.com", registration.getRight().getEmail());
        Assert.assertEquals("hashed", registration.getRight().getPassword());
    }

    @Test
    public void registerNewUserShouldReturnLeftWithUserAlreadyExists() {
        when(userRepository.findOne(Example.of(User.builder().email("some@email.com").build())))
                .thenReturn(Optional.of(User.builder().id(13L).email("some@email.com").password("hashed").build()));

        Either<String, User> registration =
                userService.registerUser(
                        RegisterUserCommand.builder()
                                .email("some@email.com")
                                .password("myWonderfulPassword")
                                .build());

        Assert.assertTrue(registration.isLeft());
        Assert.assertTrue(StringContains.containsString("already exists").matches(registration.getLeft()));
    }

    @Test
    public void obtainTokenShouldRetrieveUserFromRepositoryAndVerifyHashOfPassword() {
        when(userRepository.findOne(Example.of(User.builder().email("some@gmail.com").build())))
                .thenReturn(Optional.of(User.builder().id(3L).email("some@gmail.com").password("someHashedPass").build()));

        when(passwordEncoder.matches("rowPassword", "someHashedPass")).thenReturn(true);

        when(tokenService.createToken(User.builder().id(3L).email("some@gmail.com").password("someHashedPass").build()))
                .thenReturn("SomeTokenString");

        Either<String, String> token = userService.obtainToken(
                ObtainTokenCommand
                        .builder()
                        .email("some@gmail.com")
                        .password("rowPassword")
                        .build());

        Assert.assertTrue(token.isRight());
        Assert.assertEquals("SomeTokenString", token.getRight());
    }

    @Test
    public void obtainTokenShouldReturnBadRequestIfUserNotFound() {
        when(userRepository.findOne(Example.of(User.builder().email("some@gmail.com").build())))
                .thenReturn(Optional.empty());

        Either<String, String> token = userService.obtainToken(
                ObtainTokenCommand
                        .builder()
                        .email("some@gmail.com")
                        .password("rowPassword")
                        .build());

        Assert.assertTrue(token.isLeft());
        Assert.assertThat(token.getLeft(), containsString("Bad credentials"));
    }

    @Test
    public void obtainTokenShouldReturnBadRequestIfPasswordDoesNotMatch() {
        when(userRepository.findOne(Example.of(User.builder().email("some@gmail.com").build())))
                .thenReturn(Optional.of(User.builder().id(3L).email("some@gmail.com").password("someHashedPass").build()));

        when(passwordEncoder.matches("rowPassword", "someHashedPass")).thenReturn(false);

        Either<String, String> token = userService.obtainToken(
                ObtainTokenCommand
                        .builder()
                        .email("some@gmail.com")
                        .password("rowPassword")
                        .build());

        Assert.assertTrue(token.isLeft());
        Assert.assertThat(token.getLeft(), containsString("Bad credentials"));

        verify(passwordEncoder).matches("rowPassword", "someHashedPass");
    }
}
