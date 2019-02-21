package com.freefly19.trackdebts.bill.user;

import com.freefly19.trackdebts.bill.BillRepository;
import com.freefly19.trackdebts.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BillUserService {
    private final BillUserRepository billUserRepository;
    private final UserRepository userRepository;
    private final BillRepository billRepository;

    @Transactional
    public BillUserDto save(CreateBillUserCommand command, Long billId) {
        BillUser billUser = new BillUser();

        billUser.setUser(userRepository.getOne(command.getBillUserId()));
        billUser.setBill(billRepository.getOne(billId));
        //billUser.setCreatedAt().getTime();

        return new BillUserDto(billUserRepository.save(billUser));
    }

}
