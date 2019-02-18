package com.freefly19.trackdebts.moneytransaction;

import com.freefly19.trackdebts.bill.Bill;
import com.freefly19.trackdebts.liqpay.LiqpayOrder;
import com.freefly19.trackdebts.liqpay.LiqpayOrderLog;
import com.freefly19.trackdebts.user.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@Entity
public class MoneyTransaction {
    public static final int MAX_COMMENT_LENGTH = 200;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User sender;

    @ManyToOne(optional = false)
    private User receiver;

    @ManyToOne
    private Bill bill;

    @OneToOne
    private LiqpayOrder liqpayOrder;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(length = MAX_COMMENT_LENGTH)
    private String comment;
}
