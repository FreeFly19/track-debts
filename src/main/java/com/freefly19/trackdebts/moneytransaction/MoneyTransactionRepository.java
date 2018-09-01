package com.freefly19.trackdebts.moneytransaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction, Long> {
}
