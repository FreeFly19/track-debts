package com.freefly19.trackdebts.liqpay;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LiqpayOrderRepository extends JpaRepository<LiqpayOrder, Long> {
    Optional<LiqpayOrder> findByCode(String orderId);
}
