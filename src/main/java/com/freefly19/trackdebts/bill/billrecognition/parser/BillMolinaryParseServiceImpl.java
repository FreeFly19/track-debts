package com.freefly19.trackdebts.bill.billrecognition.parser;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BillMolinaryParseServiceImpl implements BillParse {
    @Override
    public BillParseDto parseBill(String content) {
        Pattern pattern = Pattern.compile("^Отпечатано:\\s((\\d{2}\\.){2}\\d{4}\\s\\d{2}\\:\\d{2})|(?<=\\d\\)\\s).*?(?=\\n)|(\\d*[,]\\d+)", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            // TODO: implement DTOs filling
        }
        return new BillParseDto();
    }
}
