package com.freefly19.trackdebts;

import com.freefly19.trackdebts.bill.Bill;
import com.freefly19.trackdebts.bill.BillRepository;
import com.freefly19.trackdebts.bill.item.participant.ItemParticipant;
import com.freefly19.trackdebts.bill.lock.BillLockedEvent;
import com.freefly19.trackdebts.moneytransaction.MoneyTransaction;
import com.freefly19.trackdebts.moneytransaction.MoneyTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BillLockToMoneyTransactionHandler {
    private final BillRepository billRepository;
    private final MoneyTransactionRepository moneyTransactionRepository;

    @EventListener
    public void onBillLocked(BillLockedEvent event) {
        Bill bill = billRepository.getOne(event.getBillId());

        bill.getItems().stream()
                .flatMap(billItem -> billItem.getParticipants().stream())
                .filter(p -> !p.getUser().getId().equals(event.getContext().getId()))
                .map(p -> {
                    BigDecimal coefficientSum = BigDecimal.ZERO;

                    for (ItemParticipant participant: p.getItem().getParticipants()) {
                        coefficientSum = coefficientSum.add(participant.getCoefficient());
                    }

                    return MoneyTransaction.builder()
                            .sender(p.getUser())
                            .receiver(p.getItem().getBill().getCreatedBy())
                            .amount(p.getItem().getCost().multiply(p.getCoefficient().divide(coefficientSum, RoundingMode.HALF_UP)))
                            .createdAt(event.getContext().timestamp())
                            .bill(bill)
                            .build();
                })
                .forEach(moneyTransactionRepository::save);
    }
}
