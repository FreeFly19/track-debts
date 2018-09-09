package com.freefly19.trackdebts.bill;

import com.freefly19.trackdebts.bill.item.BillItemDto;
import com.freefly19.trackdebts.user.UserDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BillDto {
    private final Long id;
    private final String title;
    private final long date;
    private final long createdAt;
    private final UserDto createdBy;
    private final List<BillItemDto> items;

    public BillDto(Bill bill) {
        this.id = bill.getId();
        this.title = bill.getTitle();
        this.date = bill.getDate().getTime();
        this.createdAt = bill.getCreatedAt().getTime();
        this.createdBy = new UserDto(bill.getCreatedBy());
        this.items = bill.getItems().stream().map(BillItemDto::new).collect(Collectors.toList());
    }
}
