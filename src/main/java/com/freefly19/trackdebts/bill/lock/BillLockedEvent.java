package com.freefly19.trackdebts.bill.lock;

import com.freefly19.trackdebts.AppEvent;
import com.freefly19.trackdebts.security.UserRequestContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BillLockedEvent implements AppEvent {
    public final long billId;
    public final UserRequestContext context;
}
