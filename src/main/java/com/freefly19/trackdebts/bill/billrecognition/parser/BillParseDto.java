package com.freefly19.trackdebts.bill.billrecognition.parser;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class BillParseDto {
    private String title;
    private Timestamp date;
    private List<BillItemParseDto> items = new ArrayList<>();
}
