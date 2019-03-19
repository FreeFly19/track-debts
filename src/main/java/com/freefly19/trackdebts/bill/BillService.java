package com.freefly19.trackdebts.bill;

import com.freefly19.trackdebts.bill.lock.BillLock;
import com.freefly19.trackdebts.bill.lock.BillLockRepository;
import com.freefly19.trackdebts.bill.lock.BillLockedEvent;
import com.freefly19.trackdebts.bill.user.BillUser;
import com.freefly19.trackdebts.bill.user.BillUserRepository;
import com.freefly19.trackdebts.security.UserRequestContext;
import com.freefly19.trackdebts.user.UserRepository;
import com.spencerwi.either.Either;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final BillUserRepository billUserRepository;
    private final BillLockRepository billLockRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public Page<BillDto> findAll(Pageable pageable, UserRequestContext context) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
        Specification<Bill> specification = (rootBill, qBill, cb) ->
                rootBill.join("billUsers").join("user").get("id").in(context.getId());

        return billRepository.findAll(specification, pageable).map(BillDto::new);
    }

    @Transactional
    public BillDto save(CreateUpdateBillCommand command, UserRequestContext context) {
        Bill bill = Bill.builder()
                .title(command.getTitle())
                .date(new Timestamp(command.getDate()))
                .createdBy(context.toUser(userRepository))
                .createdAt(context.timestamp())
                .items(new ArrayList<>())
                .build();

        bill = billRepository.save(bill);

        BillUser billUser = new BillUser();
        billUser.setBill(bill);
        billUser.setUser(context.toUser(userRepository));
        billUser.setCreatedAt(context.timestamp());
        billUserRepository.save(billUser);

        return new BillDto(bill);
    }

    @Transactional
    public Either<String, BillDto> lock(long billId, UserRequestContext context) {
        Optional<Bill> oBill = billRepository.findById(billId);

        if(!oBill.isPresent()) {
            return Either.left("Bill with " + billId +" id not found");
        }

        Bill bill = oBill.get();

        if(!bill.getCreatedBy().getId().equals(context.getId())) {
            return Either.left("You can lock only yours bills");
        }

        if (Objects.nonNull(bill.getBillLock()) && Objects.nonNull(bill.getBillLock().getId())) {
            return Either.right(new BillDto(bill));
        }

        BillLock lock = BillLock.builder()
                .bill(bill)
                .createdAt(context.timestamp())
                .build();
        bill.setBillLock(billLockRepository.save(lock));
        eventPublisher.publishEvent(new BillLockedEvent(billId, context));

        return Either.right(new BillDto(bill));
    }

    public Either<String, BillDto> get(long id, UserRequestContext context) {
        Optional<Bill> oBill = billRepository.findById(id);
        if (!oBill.isPresent()) {
            return Either.left("Bill not exists");
        }

        List<BillUser> billUsers = oBill.get().getBillUsers().stream()
                .filter(object -> object.getUser().getId() == context.getId())
                .collect(Collectors.toList());

        if (billUsers.isEmpty()) {
            return Either.left("Access is denied");
        }

        return oBill
                .map(bill -> Either.<String, BillDto>right(new BillDto(bill)))
                .orElseGet(() -> Either.left("Bill with " + id +" id not found"));
    }

    @Transactional
    public List<BillDto> search(String restaurant) {
        if (restaurant.isEmpty()) {
            return new ArrayList<>();
        }

        Specification<Bill> specification = (rootBill, qBill, cb) -> {
            Predicate billPredicate = cb.like(cb.lower(rootBill.get("title")), "%" + restaurant.toLowerCase() + "%");

            Subquery<Number> subQueryBill = qBill.subquery(Number.class);
            Root<Bill> rootSubBill = subQueryBill.from(Bill.class);

            subQueryBill.select(cb.max(rootSubBill.get("id")));
            subQueryBill.groupBy(cb.lower(rootSubBill.get("title")));

            Predicate uniqIdPredicate = cb.in(rootBill.get("id")).value(subQueryBill);

            return cb.and(billPredicate, uniqIdPredicate);
        };

        return billRepository.findAll(specification)
                .stream()
                .limit(10)
                .map(BillDto::new)
                .collect(Collectors.toList());
    }
}
