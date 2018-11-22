package com.freefly19.trackdebts.liqpay;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "liqpay_order_logs")
public class LiqpayOrderLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private LiqpayOrder order;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String data;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Timestamp createdAt;
}
