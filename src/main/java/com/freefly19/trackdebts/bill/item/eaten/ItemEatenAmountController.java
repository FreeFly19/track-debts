package com.freefly19.trackdebts.bill.item.eaten;

import com.freefly19.trackdebts.security.UserRequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ItemEatenAmountController {
    private final ItemEatenAmountService itemEatenAmountService;

    @PutMapping("/bills/{billId}/items/{itemId}/eaten-amount")
    public ResponseEntity<?> eatenAmount(@PathVariable long billId,
                                         @PathVariable long itemId,
                                         @RequestBody ItemEatenAmountCommand command,
                                         UserRequestContext context) {

        return itemEatenAmountService.specifyEatenAmount(itemId, command, context)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(ResponseEntity.ok()::build);
    }
}
