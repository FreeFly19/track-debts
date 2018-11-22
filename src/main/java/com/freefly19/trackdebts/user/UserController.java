package com.freefly19.trackdebts.user;

import com.freefly19.trackdebts.AppError;
import com.freefly19.trackdebts.moneytransaction.MoneyTransactionService;
import com.freefly19.trackdebts.security.UserRequestContext;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MoneyTransactionService moneyTransactionService;

    @ApiOperation(value = "Register new User", response = UserDto.class)
    @PostMapping("/users")
    ResponseEntity<?> register(@RequestBody @Valid RegisterUserCommand command) {
        return userService
                .registerUser(command)
                .map(AppError::new, UserDto::new)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }

    @ApiOperation(value = "Obtain Token", response = TokenDto.class)
    @PostMapping("/users/token")
    ResponseEntity<?> token(@RequestBody ObtainTokenCommand command) {
        return userService.obtainToken(command)
                .map(AppError::new, Function.identity())
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }

    @ApiOperation("Return User List")
    @GetMapping("/users")
    List<UserDto> allUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/current")
    UserExtendedDto currentUser(@ApiIgnore UserRequestContext context) {
        return userService.getById(context.getId());
    }

    @PutMapping("/users/current")
    UserExtendedDto updateCurrentUser(@RequestBody @Valid UpdateUserInfoCommand cmd,
                                      @ApiIgnore UserRequestContext context) {
        return userService.update(cmd, context);
    }


    @GetMapping("/users/current/balance")
    List<UserBalanceDto> balance(UserRequestContext context) {
        return moneyTransactionService.getBalance(context);
    }
}
