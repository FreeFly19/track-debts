package com.freefly19.trackdebts.bill.item;

import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
public class BillItemDto {
    private final Long id;
    private final String title;
    private final BigDecimal price;
    private final Timestamp createdAt;

    public BillItemDto(BillItem billItem) {
        this.id = billItem.getId();
        this.title = billItem.getTitle();
        this.createdAt = billItem.getCreatedAt();
        this.price = billItem.getPrice();
    }
}
