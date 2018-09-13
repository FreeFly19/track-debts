package com.freefly19.trackdebts.user;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserBalanceDto {
    private final UserDto user;
    private final BigDecimal balance;


    public UserBalanceDto(User user, BigDecimal balance) {
        this.user = new UserDto(user);
        this.balance = balance;
    }
}
