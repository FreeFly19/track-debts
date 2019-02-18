package com.freefly19.trackdebts.moneytransaction;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

import static com.freefly19.trackdebts.moneytransaction.MoneyTransaction.MAX_COMMENT_LENGTH;

@Data
public class ReceiveMoneyCommand {
    private long senderId;

    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal amount;

    @Length(max = MAX_COMMENT_LENGTH)
    private String comment;
}
