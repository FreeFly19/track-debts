package com.freefly19.trackdebts.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class Email {
    private final String sender;
    private final String receiver;
    private final String subject;
    private final String body;
}
