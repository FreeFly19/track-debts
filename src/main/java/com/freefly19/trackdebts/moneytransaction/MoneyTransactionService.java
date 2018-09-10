package com.freefly19.trackdebts.moneytransaction;

import com.freefly19.trackdebts.security.UserRequestContext;
import com.freefly19.trackdebts.user.User;
import com.freefly19.trackdebts.user.UserRepository;
import com.spencerwi.either.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MoneyTransactionService {
    private final UserRepository userRepository;
    private final MoneyTransactionRepository moneyTransactionRepository;

    public Page<MoneyTransactionDto> findAll(Pageable pageable, UserRequestContext context) {
        Specification<MoneyTransaction> spec = Specification.where((r, q, cb) ->
                cb.or(
                        cb.equal(r.get("receiver").get("id"), context.getId()),
                        cb.equal(r.get("sender").get("id"), context.getId())
                )
        );

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());

        return moneyTransactionRepository.findAll(spec, pageable)
                .map(MoneyTransactionDto::new);
    }

    public Optional<String> receiveMoney(ReceiveMoneyCommand command, UserRequestContext context) {
        if (command.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return Optional.of("Amount should be an positive number");
        }

        Optional<User> oSender = userRepository.findById(command.getSenderId());
        if (!oSender.isPresent()) {
            return Optional.of("Sender not found");
        }

        moneyTransactionRepository.save(
                MoneyTransaction.builder()
                        .amount(command.getAmount())
                        .createdAt(context.timestamp())
                        .receiver(context.toUser(userRepository))
                        .sender(oSender.get())
                        .build()
        );

        return Optional.empty();
    }
}
