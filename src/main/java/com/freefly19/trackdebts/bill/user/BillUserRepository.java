package com.freefly19.trackdebts.bill.user;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillUserRepository extends JpaRepository<BillUser, Long> {
    List<BillUser> findAll(Specification<BillUser> specification);
}
