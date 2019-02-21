package com.freefly19.trackdebts.bill.user;

import com.freefly19.trackdebts.bill.BillRepository;
import com.freefly19.trackdebts.user.UserDto;
import com.freefly19.trackdebts.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BillUserService {
    private final BillUserRepository billUserRepository;
    private final UserRepository userRepository;
    private final BillRepository billRepository;

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return billUserRepository.findAll()
                .stream()
                .map(billUser -> new UserDto(billUser.getUser()))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto save(CreateBillUserCommand command, Long billId) {
        BillUser billUser = new BillUser();

        billUser.setUser(userRepository.getOne(command.getBillUserId()));
        billUser.setBill(billRepository.getOne(billId));
        billUser.setCreatedAt(new Timestamp(new Date().getTime()));

        return new UserDto(billUserRepository.save(billUser).getUser());
    }
}
