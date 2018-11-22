package com.freefly19.trackdebts.user;

import lombok.Getter;

@Getter
public class UserExtendedDto extends UserDto {
    private final String cardNumber;

    public UserExtendedDto(User user) {
        super(user);
        this.cardNumber = user.getCardNumber();
    }
}
