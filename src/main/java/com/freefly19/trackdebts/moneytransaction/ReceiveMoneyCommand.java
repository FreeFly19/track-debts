package com.freefly19.trackdebts.moneytransaction;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ReceiveMoneyCommand {
    private long senderId;

    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal amount;

    @Length(message = "", max = 300)
    private String comment;
}
