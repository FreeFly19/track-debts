package com.freefly19.trackdebts.moneytransaction;

import com.freefly19.trackdebts.AppError;
import com.freefly19.trackdebts.security.UserRequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MoneyTransactionController {
    private final MoneyTransactionService service;

    @GetMapping("/transactions")
    private Page<MoneyTransactionDto> moneyTransactions(Pageable pageable, UserRequestContext context) {
        return service.findAll(pageable, context);
    }

    @PostMapping("/transactions")
    private ResponseEntity<?> receiveMoney(@RequestBody @Valid ReceiveMoneyCommand command, UserRequestContext context) {
        return service.receiveMoney(command, context)
                .map(AppError::new)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(ResponseEntity.ok()::build);
    }
}
