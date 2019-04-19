package com.freefly19.trackdebts.bill.billrecognition;

import lombok.Data;

@Data
public class ReceiveImageCommand {
    private String imageInBase64;
}
