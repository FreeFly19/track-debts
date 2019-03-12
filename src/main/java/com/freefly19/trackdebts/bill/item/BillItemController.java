package com.freefly19.trackdebts.bill.item;

import com.freefly19.trackdebts.AppError;
import com.freefly19.trackdebts.security.UserRequestContext;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
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

    @DeleteMapping("/bills/{\\d}/items/{itemId}")
    public ResponseEntity<?> addBillItem(@PathVariable Long itemId, @ApiIgnore UserRequestContext context) {
        return billService.delete(itemId, context)
                .map(AppError::new)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(ResponseEntity.ok()::build);
    }

    @GetMapping("/bills/{billId}/autocompleteItem")
    public ResponseEntity<List<BillItemDto>> autocompleteProductItem(@PathVariable Long billId, @RequestParam String product) {
        return ResponseEntity.ok(billService.search(billId, product));
    }
}
