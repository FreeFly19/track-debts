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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final BillLockRepository billLockRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public Page<BillDto> findAll(Pageable pageable) {
        return billRepository.findAll(pageable).map(BillDto::new);
    }

    @Transactional
    public BillDto save(CreateUpdateBillCommand command, UserRequestContext context) {
        Bill bill = Bill.builder()
                .title(command.getTitle())
                .date(new Timestamp(command.getDate()))
                .createdBy(context.toUser(userRepository))
                .createdAt(context.timestamp())
                .build();

        return new BillDto(billRepository.save(bill));
    }

    @Transactional
    public Either<String, BillDto> lock(long billId, UserRequestContext context) {
        return billRepository.findById(billId)
                .map(bill -> {
                    BillLock lock = BillLock.builder()
                            .bill(bill)
                            .createdAt(context.timestamp())
                            .build();
                    bill.setBillLock(billLockRepository.save(lock));
                    eventPublisher.publishEvent(new BillLockedEvent(billId, context));

                    return Either.<String, BillDto>right(new BillDto(bill));
                })
                .orElseGet(() -> Either.left("Bill with " + billId +" id not found"));
    }
}
