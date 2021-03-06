package com.freefly19.trackdebts.bill.item;

import com.freefly19.trackdebts.bill.Bill;
import com.freefly19.trackdebts.bill.BillRepository;
import com.freefly19.trackdebts.bill.item.participant.ItemParticipantRepository;
import com.freefly19.trackdebts.security.UserRequestContext;
import com.freefly19.trackdebts.util.DateTimeProvider;
import com.spencerwi.either.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BillItemService {
    private final BillItemRepository billItemRepository;
    private final BillRepository billRepository;
    private final ItemParticipantRepository participantRepository;
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
                            .participants(new HashSet<>())
                            .build();

                    BillItemDto billItemDto = new BillItemDto(billItemRepository.save(billItem));

                    return Either.<String, BillItemDto>right(billItemDto);
                })
                .orElseGet(() -> Either.left("Bill with " + billId + " id not found"));
    }

    public Optional<String> delete(Long itemId, UserRequestContext context) {
        Optional<BillItem> oItem = billItemRepository.findById(itemId);

        if (!oItem.isPresent()) {
            return Optional.of("Item with " + itemId + " not found");
        }

        BillItem item = oItem.get();

        if (!item.getBill().getCreatedBy().getId().equals(context.getId())) {
            return Optional.of("You cannot delete item from a strangers bill");
        }

        if (item.getBill().getBillLock() != null && item.getBill().getBillLock().getId() != null) {
            return Optional.of("You cannot delete item from locked bill");
        }

        item.getParticipants().forEach(participantRepository::delete);

        billItemRepository.delete(item);

        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public List<BillItemDto> search(Long billId, String product) {
        if (product.isEmpty()) {
            return new ArrayList<>();
        }

        Optional<Bill> currentBill = billRepository.findById(billId);
        if (!currentBill.isPresent()) {
            return new ArrayList<>();
        }

        Specification<BillItem> specification = (rootBillItem, qBillItem, cb) -> {
            Predicate billItemPredicate = cb.like(cb.lower(rootBillItem.get("title")), "%" + product.toLowerCase() + "%");
            Predicate billPredicate = cb.equal(cb.lower(rootBillItem.get("bill").get("title")), currentBill.get().getTitle().toLowerCase());
            Predicate and1 = cb.and(billItemPredicate, billPredicate);

            Subquery<Number> subQueryBillItem = qBillItem.subquery(Number.class);
            Root<BillItem> rootSubBillItem = subQueryBillItem.from(BillItem.class);

            subQueryBillItem.select(cb.max(rootSubBillItem.get("createdAt")));
            subQueryBillItem.groupBy(cb.lower(rootSubBillItem.get("title")));
            Predicate and2 = cb.in(rootBillItem.get("createdAt")).value(subQueryBillItem);

            return cb.and(and1, and2);
        };

        return billItemRepository.findAll(specification)
                .stream()
                .limit(5)
                .map(BillItemDto::new)
                .collect(Collectors.toList());
    }
}
