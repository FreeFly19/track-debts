package com.freefly19.trackdebts.bill.user;

import com.freefly19.trackdebts.bill.Bill;
import com.freefly19.trackdebts.bill.BillRepository;
import com.freefly19.trackdebts.security.UserRequestContext;
import com.freefly19.trackdebts.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BillUserService {
    private final BillUserRepository billUserRepository;
    private final UserRepository userRepository;
    private final BillRepository billRepository;

    @Transactional
    public Optional<BillUserDto> save(UserRequestContext context, CreateBillUserCommand command, Long billId) {
        if (!billRepository.getOne(billId).getCreatedBy().getId().equals(context.getId())) {
            return Optional.empty();
        }
        Specification<BillUser> specification = (rootBillUser, qBillUser, cb) -> {
            Predicate userPredicate = cb.equal(rootBillUser.get("user").get("id"), command.getUserId());
            Predicate billPredicate = cb.equal(rootBillUser.get("bill").get("id"), billId);

            return cb.and(userPredicate, billPredicate);
        };

        BillUser billUser = billUserRepository.findAll(specification)
                .stream()
                .findAny()
                .orElseGet(() -> {
                    BillUser bu = new BillUser();

                    bu.setUser(userRepository.getOne(command.getUserId()));
                    bu.setBill(billRepository.getOne(billId));
                    bu.setCreatedAt(new Timestamp(new Date().getTime()));

                    return billUserRepository.save(bu);
                });

        return Optional.of(new BillUserDto(billUser));
    }

    @Transactional
    public boolean delete(UserRequestContext context, Long billUserId) {
        Bill bill = billUserRepository.getOne(billUserId).getBill();

        if (!bill.getCreatedBy().getId().equals(context.getId())) {
            return false;
        }

        billUserRepository.deleteById(billUserId);
        return true;
    }

    @Transactional(readOnly = true)
    public List<BillUserDto> findByBillId(long billId) {
        Specification<BillUser> billUserSpecification = (root, query, cb) ->
                cb.equal(root.get("bill").get("id"), billId);

        return billUserRepository.findAll(billUserSpecification)
                .stream()
                .map(BillUserDto::new)
                .collect(Collectors.toList());
    }
}
