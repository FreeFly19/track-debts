package com.freefly19.trackdebts.bill.item;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {
    List<BillItem> findAll(Specification<BillItem> specification);
}
