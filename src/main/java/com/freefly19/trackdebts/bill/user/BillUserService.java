package com.freefly19.trackdebts.bill.user;

import com.freefly19.trackdebts.bill.BillRepository;
import com.freefly19.trackdebts.user.UserDto;
import com.freefly19.trackdebts.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
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
        Specification<BillUser> specification = (rootBillUser, qBillUser, cb) -> {
            Predicate predicate = cb.equal(rootBillUser.get("user").get("id"), command.getBillUserId());
            Predicate predicate2 = cb.equal(rootBillUser.get("bill").get("id"), billId);

            return cb.and(predicate, predicate2);
        };

        List<BillUser> billUsers = billUserRepository.findAll(specification);

        BillUser billUser;
        if (billUsers.isEmpty()) {
            billUser = new BillUser();

            billUser.setUser(userRepository.getOne(command.getBillUserId()));
            billUser.setBill(billRepository.getOne(billId));
            billUser.setCreatedAt(new Timestamp(new Date().getTime()));

            return new UserDto(billUserRepository.save(billUser).getUser());
        }
        return new UserDto(billUsers.get(0).getUser());
    }

    @Transactional
    public void delete(Long billUserId, Long billId) {
        Specification<BillUser> specification = (rootBillUser, qBillUser, cb) -> {
            Predicate predicate = cb.equal(rootBillUser.get("user").get("id"), billUserId);
            Predicate predicate2 = cb.equal(rootBillUser.get("bill").get("id"), billId);

            return cb.and(predicate, predicate2);
        };

        List<BillUser> billUsers = billUserRepository.findAll(specification);

        for (BillUser user : billUsers) {
            billUserRepository.deleteById(user.getId());
        }
    }
}
