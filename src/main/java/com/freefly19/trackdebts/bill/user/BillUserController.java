package com.freefly19.trackdebts.bill.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class BillUserController {
    private final BillUserService billUserService;

    @PostMapping("/bill/{billId}/users")
    public ResponseEntity<BillUserDto> createBillUsers(@RequestBody @Valid CreateBillUserCommand command, @PathVariable Long billId) {
        return ResponseEntity.ok(billUserService.save(command, billId));
    }
}
