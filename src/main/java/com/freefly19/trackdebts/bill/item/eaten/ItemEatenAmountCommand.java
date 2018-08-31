package com.freefly19.trackdebts.bill.item.eaten;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ItemEatenAmountCommand {
    @NotNull
    @Min(1)
    private Integer amount;
}
