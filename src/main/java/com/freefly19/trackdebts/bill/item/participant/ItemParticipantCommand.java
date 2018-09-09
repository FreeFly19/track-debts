package com.freefly19.trackdebts.bill.item.participant;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ItemParticipantCommand {
    @NotNull
    @Min(1)
    private Integer coefficient;
}
