package com.freefly19.trackdebts.bill.user;

import com.freefly19.trackdebts.bill.Bill;
import com.freefly19.trackdebts.user.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class BillUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Bill bill;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private Timestamp createdAt;
}
