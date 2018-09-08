package com.freefly19.trackdebts.moneytransaction;

import com.freefly19.trackdebts.security.UserRequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Specification<MoneyTransaction> spec = Specification.where( (r, q, cb) ->
                cb.or(
                        cb.equal(r.get("receiver").get("id"), context.getId()),
                        cb.equal(r.get("sender").get("id"), context.getId())
                )
        );

        Page<MoneyTransaction> all = moneyTransactionRepository.findAll(spec, pageable);

        return all.map(MoneyTransactionDto::new);
    }
}
