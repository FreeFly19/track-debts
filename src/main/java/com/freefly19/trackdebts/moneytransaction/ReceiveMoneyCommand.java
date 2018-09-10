package com.freefly19.trackdebts.moneytransaction;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
public class ReceiveMoneyCommand {
    private long senderId;

    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal amount;
}
