package com.freefly19.trackdebts.bill;

import com.freefly19.trackdebts.bill.lock.BillLock;
import com.freefly19.trackdebts.bill.lock.BillLockRepository;
import com.freefly19.trackdebts.bill.lock.BillLockedEvent;
import com.freefly19.trackdebts.security.UserRequestContext;
import com.freefly19.trackdebts.user.UserRepository;
import com.spencerwi.either.Either;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final BillLockRepository billLockRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public Page<BillDto> findAll(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
        return billRepository.findAll(pageable).map(BillDto::new);
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

        return new BillDto(billRepository.save(bill));
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
            return Either.<String, BillDto>right(new BillDto(bill));
        }

        BillLock lock = BillLock.builder()
                .bill(bill)
                .createdAt(context.timestamp())
                .build();
        bill.setBillLock(billLockRepository.save(lock));
        eventPublisher.publishEvent(new BillLockedEvent(billId, context));

        return Either.<String, BillDto>right(new BillDto(bill));
    }

    public Either<String, BillDto> get(long id, UserRequestContext context) {
        return billRepository.findById(id)
                .map(bill -> Either.<String, BillDto>right(new BillDto(bill)))
                .orElseGet(() -> Either.left("Bill with " + id +" id not found"));
    }
}
