package com.freefly19.trackdebts.bill.item;

import com.freefly19.trackdebts.bill.item.participant.ItemParticipantDto;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BillItemDto {
    private final Long id;
    private final String title;
    private final BigDecimal cost;
    private final BigDecimal amount;
    private final Timestamp createdAt;
    private final List<ItemParticipantDto> participants;

    public BillItemDto(BillItem billItem) {
        this.id = billItem.getId();
        this.title = billItem.getTitle();
        this.createdAt = billItem.getCreatedAt();
        this.cost = billItem.getCost();
        this.amount = billItem.getAmount();
        this.participants = billItem.getParticipants()
                .stream()
                .map(ItemParticipantDto::new)
                .collect(Collectors.toList());
    }
}
