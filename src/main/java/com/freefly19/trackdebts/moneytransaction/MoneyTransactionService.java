package com.freefly19.trackdebts.moneytransaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MoneyTransactionService {
    private final MoneyTransactionRepository moneyTransactionRepository;
}
