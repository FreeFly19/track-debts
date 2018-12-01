package com.freefly19.trackdebts.moneytransaction;

import com.freefly19.trackdebts.bill.BillDto;
import com.freefly19.trackdebts.user.UserDto;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class MoneyTransactionDto {
    private final long id;
    private final UserDto sender;
    private final UserDto receiver;
    private final BigDecimal amount;
    private final long createdAt;
    private final BillDto bill;
    private final MoneyTransactionType type;

    public MoneyTransactionDto(MoneyTransaction moneyTransaction) {
        this.id = moneyTransaction.getId();
        this.sender = new UserDto(moneyTransaction.getSender());
        this.receiver = new UserDto(moneyTransaction.getReceiver());
        this.amount = moneyTransaction.getAmount();
        this.createdAt = moneyTransaction.getCreatedAt().getTime();
        this.bill = Objects.nonNull(moneyTransaction.getBill()) ? new BillDto(moneyTransaction.getBill()) : null;

        if (this.bill != null) {
            this.type = MoneyTransactionType.BILL;
        } else if (Objects.nonNull(moneyTransaction.getLiqpayOrder()) && Objects.nonNull(moneyTransaction.getLiqpayOrder().getId())) {
            this.type = MoneyTransactionType.LIQPAY;
        } else {
            this.type = MoneyTransactionType.MANUAL;
        }
    }
}
