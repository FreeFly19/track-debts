package com.freefly19.trackdebts;

import com.freefly19.trackdebts.bill.Bill;
import com.freefly19.trackdebts.bill.BillRepository;
import com.freefly19.trackdebts.bill.lock.BillLockedEvent;
import com.freefly19.trackdebts.moneytransaction.MoneyTransaction;
import com.freefly19.trackdebts.moneytransaction.MoneyTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;

@RequiredArgsConstructor
@Service
public class BillLockToMoneyTransactionHandler {
    private final BillRepository billRepository;
    private final MoneyTransactionRepository moneyTransactionRepository;

    @EventListener
    public void onBillLocked(BillLockedEvent event) {
        Bill bill = billRepository.getOne(event.getBillId());

        bill.getItems().stream()
                .flatMap(billItem -> billItem.getEatenAmounts().stream())
                .map(itemEatenAmount -> MoneyTransaction.builder()
                        .sender(itemEatenAmount.getUser())
                        .receiver(itemEatenAmount.getItem().getBill().getCreatedBy())
                        .amount(itemEatenAmount.getItem().getCost().divide(itemEatenAmount.getValue(), RoundingMode.HALF_UP))
                        .createdAt(event.getContext().timestamp())
                        .build())
                .forEach(moneyTransactionRepository::save);
    }
}
