package com.freefly19.trackdebts.user;

import lombok.Getter;

@Getter
public class UserDto {
    private final long id;
    private final String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }
}
