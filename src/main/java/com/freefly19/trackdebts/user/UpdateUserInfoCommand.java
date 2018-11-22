package com.freefly19.trackdebts.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;

@Data
public class UpdateUserInfoCommand {

    @Length(min = 1)
    private String firstName;

    @Length(min = 1)
    private String lastName;

    private String cardNumber;

    @AssertTrue(message = "Card Number has invalid format")
    private boolean isCardNumberValid() {
        return cardNumber == null || cardNumber.matches("\\d{16}");
    }
}
