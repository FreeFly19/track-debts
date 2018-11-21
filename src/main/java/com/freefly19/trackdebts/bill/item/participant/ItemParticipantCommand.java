package com.freefly19.trackdebts.bill.item.participant;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ItemParticipantCommand {
    @NotNull
    @Min(0)
    private BigDecimal coefficient;
}
