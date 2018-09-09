package com.freefly19.trackdebts.bill.item.participant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.freefly19.trackdebts.user.UserDto;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ItemParticipantDto {
    private final UserDto user;

    @JsonFormat (shape= JsonFormat.Shape.STRING)
    private final BigDecimal coefficient;

    public ItemParticipantDto(ItemParticipant itemParticipant) {
        this.user = new UserDto(itemParticipant.getUser());
        this.coefficient = itemParticipant.getCoefficient();
    }
}
