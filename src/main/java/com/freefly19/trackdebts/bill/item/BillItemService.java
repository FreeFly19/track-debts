package com.freefly19.trackdebts.bill.item;

import com.freefly19.trackdebts.bill.BillRepository;
import com.spencerwi.either.Either;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BillItemService {
    private final BillItemRepository billItemRepository;
    private final BillRepository billRepository;

    @Transactional
    public Either<String, BillItemDto> save(Long billId, CreateUpdateBillItemCommand command) {
        return billRepository.findById(billId)
                .map(bill -> {
                    BillItem billItem = BillItem.builder()
                            .bill(bill)
                            .title(command.getTitle())
                            .price(command.getPrice())
                            .build();

                    BillItemDto billItemDto = new BillItemDto(billItemRepository.save(billItem));

                    return Either.<String, BillItemDto>right(billItemDto);
                })
                .orElseGet(() -> Either.left("Bill with " + billId + " id not found"));
    }
}
