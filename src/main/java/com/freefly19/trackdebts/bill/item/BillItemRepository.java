package com.freefly19.trackdebts.bill.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {
    Page<BillItem> findAll(Specification<BillItem> specification, Pageable limit);
}
