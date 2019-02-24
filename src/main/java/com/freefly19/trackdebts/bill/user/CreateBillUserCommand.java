package com.freefly19.trackdebts.bill.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateBillUserCommand {
    @NotNull
    private Long userId;
}
