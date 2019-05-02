package com.freefly19.trackdebts.bill.billrecognition.parser;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillItemParseDto {
    private String title;
    private BigDecimal cost;
    private BigDecimal amount;
}
