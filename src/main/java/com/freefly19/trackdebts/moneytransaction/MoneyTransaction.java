package com.freefly19.trackdebts.moneytransaction;

import com.freefly19.trackdebts.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@Getter @Setter
@Entity
public class MoneyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User sender;

    @ManyToOne(optional = false)
    private User receiver;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Timestamp createdAt;
}
