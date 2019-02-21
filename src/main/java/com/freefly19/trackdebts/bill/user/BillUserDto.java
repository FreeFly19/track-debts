package com.freefly19.trackdebts.bill.user;

import com.freefly19.trackdebts.bill.BillDto;
import com.freefly19.trackdebts.user.UserDto;
import lombok.Getter;

@Getter
public class BillUserDto {
    private final Long id;
    private final BillDto bill;
    private final UserDto user;
    //private final long createdAt;

    public BillUserDto(BillUser billUser) {
        this.id = billUser.getId();
        this.bill = new BillDto(billUser.getBill());
        this.user = new UserDto(billUser.getUser());
        //this.createdAt = billUser.getCreatedAt().getTime();
    }
}
