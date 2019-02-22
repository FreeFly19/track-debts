package com.freefly19.trackdebts.bill.user;

import com.freefly19.trackdebts.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class BillUserController {
    private final BillUserService billUserService;

    @GetMapping("bills/{billId}/users")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(billUserService.findAll());
    }

    @PutMapping("/bills/{billId}/users")
    public ResponseEntity<UserDto> createBillUsers(@RequestBody @Valid CreateBillUserCommand command, @PathVariable Long billId) {
        return ResponseEntity.ok(billUserService.save(command, billId));
    }

    @DeleteMapping("/bills/{billId}/users/{billUserId}")
    public ResponseEntity<Void> deleteBillUser(@PathVariable Long billUserId, @PathVariable Long billId) {
        billUserService.delete(billUserId, billId);

        return ResponseEntity.ok().build();
    }
}
