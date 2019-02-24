package com.freefly19.trackdebts.bill.user;

import com.freefly19.trackdebts.user.UserDto;
import lombok.Data;

@Data
public class BillUserDto {
    private long id;
    private UserDto user;

    public BillUserDto(BillUser billUser) {
        this.id = billUser.getId();
        this.user = new UserDto(billUser.getUser());
    }
}
