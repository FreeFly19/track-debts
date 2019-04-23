package com.freefly19.trackdebts.bill.billrecognition.parser;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BillMolinaryParseServiceImpl implements BillParse {
    @Override
    public BillParseDto parseBill(String content) {
        Pattern pattern = Pattern.compile("");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            // TODO: matcher.group();
        }
        return new BillParseDto();
    }
}
