package com.freefly19.trackdebts.moneytransaction;

import com.freefly19.trackdebts.security.UserRequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MoneyTransactionController {
    private final MoneyTransactionService service;

    @GetMapping("/transactions")
    private Page<MoneyTransactionDto> moneyTransactions(Pageable pageable, UserRequestContext context) {
        return service.findAll(pageable, context);
    }
}
