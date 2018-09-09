package com.freefly19.trackdebts.bill.item;

import com.freefly19.trackdebts.bill.BillRepository;
import com.freefly19.trackdebts.util.DateTimeProvider;
import com.spencerwi.either.Either;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Service
public class BillItemService {
    private final BillItemRepository billItemRepository;
    private final BillRepository billRepository;
    private final DateTimeProvider dateTimeProvider;

    @Transactional
    public Either<String, BillItemDto> save(Long billId, CreateUpdateBillItemCommand command) {
        return billRepository.findById(billId)
                .map(bill -> {
                    BillItem billItem = BillItem.builder()
                            .bill(bill)
                            .title(command.getTitle())
                            .cost(command.getCost())
                            .amount(command.getAmount())
                            .createdAt(new Timestamp(dateTimeProvider.now()))
                            .build();

                    BillItemDto billItemDto = new BillItemDto(billItemRepository.save(billItem));

                    return Either.<String, BillItemDto>right(billItemDto);
                })
                .orElseGet(() -> Either.left("Bill with " + billId + " id not found"));
    }
}
