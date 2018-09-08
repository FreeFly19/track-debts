package com.freefly19.trackdebts.bill.item;

import com.freefly19.trackdebts.bill.Bill;
import com.freefly19.trackdebts.bill.item.eaten.ItemEatenAmount;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@Entity
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Bill bill;

    @OneToMany(mappedBy = "item")
    private Set<ItemEatenAmount> eatenAmounts;
}
