package com.freefly19.trackdebts.bill;

import lombok.Data;

@Data
public class CreateUpdateBillCommand {
    private String title;
    private long date;
}
