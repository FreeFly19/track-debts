package com.freefly19.trackdebts.eventhandlers;

import com.freefly19.trackdebts.bill.Bill;
import com.freefly19.trackdebts.bill.BillRepository;
import com.freefly19.trackdebts.bill.item.participant.ItemParticipant;
import com.freefly19.trackdebts.bill.lock.BillLockedEvent;
import com.freefly19.trackdebts.moneytransaction.MoneyTransaction;
import com.freefly19.trackdebts.moneytransaction.MoneyTransactionRepository;
import com.freefly19.trackdebts.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class BillLockToMoneyTransactionHandler {
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final MoneyTransactionRepository moneyTransactionRepository;

    @EventListener
    public void onBillLocked(BillLockedEvent event) {
        Bill bill = billRepository.getOne(event.getBillId());

        HashMap<Long, BigDecimal> userIdAmountMap = new HashMap<>();

        bill.getItems().stream()
                .flatMap(billItem -> billItem.getParticipants().stream())
                .filter(p -> !p.getUser().getId().equals(event.getContext().getId()))
                .forEach(p -> {
                    BigDecimal coefficientSum = BigDecimal.ZERO;

                    for (ItemParticipant participant : p.getItem().getParticipants()) {
                        coefficientSum = coefficientSum.add(participant.getCoefficient());
                    }

                    BigDecimal amount = userIdAmountMap.getOrDefault(p.getUser().getId(), new BigDecimal(0));

                    BigDecimal amountPerItem = p.getItem().getCost()
                            .multiply(p.getCoefficient().divide(coefficientSum, 5, RoundingMode.HALF_UP));

                    userIdAmountMap.put(p.getUser().getId(), amount.add(amountPerItem));
                });

        userIdAmountMap.forEach((userId, amount) -> {
            moneyTransactionRepository.save(
                    MoneyTransaction.builder()
                            .sender(bill.getCreatedBy())
                            .receiver(userRepository.getOne(userId))
                            .amount(amount)
                            .createdAt(event.getContext().timestamp())
                            .bill(bill)
                            .build()
            );
        });
    }
}
