package com.freefly19.trackdebts.bill.billrecognition.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillParseService {
    private final BillMolinaryParseServiceImpl billMolinary;

    public void selectBillParser(String content) {
        billMolinary.parseBill(content);
    }
}
