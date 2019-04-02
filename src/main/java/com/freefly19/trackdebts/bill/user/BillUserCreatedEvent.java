package com.freefly19.trackdebts.bill.user;

import com.freefly19.trackdebts.AppEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BillUserCreatedEvent implements AppEvent {
    private final long billUserId;
}
