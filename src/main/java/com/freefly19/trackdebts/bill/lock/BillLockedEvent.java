package com.freefly19.trackdebts.bill.lock;

import com.freefly19.trackdebts.AppEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BillLockedEvent implements AppEvent {
    public final long billId;
    public final long userId;
}
