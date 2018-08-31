package com.freefly19.trackdebts.bill.item;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateUpdateBillItemCommand {
    @NotNull
    @Length(min = 2, max = 255)
    private String title;

    @NotNull
    private BigDecimal price;
}
