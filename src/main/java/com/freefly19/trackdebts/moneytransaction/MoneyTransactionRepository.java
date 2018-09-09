package com.freefly19.trackdebts.moneytransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction, Long>, JpaSpecificationExecutor<MoneyTransaction> {
}
