package com.freefly19.trackdebts.user;

import com.freefly19.trackdebts.bill.user.BillUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String cardNumber;

    @OneToMany(mappedBy = "user")
    private List<BillUser> billUsers = new ArrayList<>();

}
