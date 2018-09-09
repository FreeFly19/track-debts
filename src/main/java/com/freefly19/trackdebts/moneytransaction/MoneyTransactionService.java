package com.freefly19.trackdebts.moneytransaction;

import com.freefly19.trackdebts.security.UserRequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
@Service
public class MoneyTransactionService {
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
}
