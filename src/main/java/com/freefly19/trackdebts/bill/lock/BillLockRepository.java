package com.freefly19.trackdebts.bill.lock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillLockRepository extends JpaRepository<BillLock, Long> {
}
