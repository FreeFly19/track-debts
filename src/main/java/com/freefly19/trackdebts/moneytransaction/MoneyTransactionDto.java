package com.freefly19.trackdebts.moneytransaction;

import com.freefly19.trackdebts.user.UserDto;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MoneyTransactionDto {
    private final long id;
    private final UserDto sender;
    private final UserDto receiver;
    private final BigDecimal amount;
    private final long createdAt;

    public MoneyTransactionDto(MoneyTransaction moneyTransaction) {
        this.id = moneyTransaction.getId();
        this.sender = new UserDto(moneyTransaction.getSender());
        this.receiver = new UserDto(moneyTransaction.getReceiver());
        this.amount = moneyTransaction.getAmount();
        this.createdAt = moneyTransaction.getCreatedAt().getTime();
    }
}
