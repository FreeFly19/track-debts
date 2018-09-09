package com.freefly19.trackdebts.bill.item;

import com.freefly19.trackdebts.AppError;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@AllArgsConstructor
public class BillItemController {
    private final BillItemService billService;

    @PostMapping("/bills/{billId}/items")
    public ResponseEntity<?> addBillItem(@PathVariable Long billId, @RequestBody CreateUpdateBillItemCommand command) {
        return billService.save(billId, command)
                .map(AppError::new, Function.identity())
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }
}
