package com.freefly19.trackdebts.user;

import lombok.Getter;

import java.util.Optional;

@Getter
public class UserDto {
    private final long id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final boolean canAcceptPayment;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.canAcceptPayment = user.getCardNumber() != null;
    }

    public String getFullName() {
        if (firstName == null) {
            return Optional.ofNullable(lastName).orElse(null);
        }
        return firstName + (lastName == null ? "" : (" " + lastName));
    }

    public String getDisplayName() {
        return Optional.ofNullable(getFullName()).orElse(email);
    }
}
