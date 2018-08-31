package com.freefly19.trackdebts.bill;

import com.freefly19.trackdebts.user.UserDto;
import lombok.Getter;

@Getter
public class BillDto {
    private final Long id;
    private final String title;
    private final long createdAt;
    private final long date;
    private final UserDto createdBy;

    public BillDto(Bill bill) {
        this.id = bill.getId();
        this.title = bill.getTitle();
        this.date = bill.getDate().getTime();
        this.createdAt = bill.getCreatedAt().getTime();
        this.createdBy = new UserDto(bill.getCreatedBy());
    }
}
