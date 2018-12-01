package com.freefly19.trackdebts.liqpay;

import lombok.Data;

@Data
public class DataWithSig {
    private final String data;
    private final String signature;

    public DataWithSig(String data, String signature) {
        this.data = data;
        this.signature = signature;
    }
}
