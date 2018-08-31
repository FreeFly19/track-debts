package com.freefly19.trackdebts.bill.lock;

import com.freefly19.trackdebts.bill.Bill;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Getter @Setter
@Entity
public class BillLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Bill bill;

    @Column(nullable = false)
    private Timestamp createdAt;
}
