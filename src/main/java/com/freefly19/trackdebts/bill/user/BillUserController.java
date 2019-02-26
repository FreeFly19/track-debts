package com.freefly19.trackdebts.bill.user;

import com.freefly19.trackdebts.AppError;
import com.freefly19.trackdebts.security.UserRequestContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@RestController
public class BillUserController {
    private final BillUserService billUserService;

    @GetMapping("bills/{billId}/users")
    public ResponseEntity<List<BillUserDto>> findAll(@PathVariable long billId) {
        return ResponseEntity.ok(billUserService.findByBillId(billId));
    }

    @PutMapping("/bills/{billId}/users")
    public ResponseEntity<?> createBillUsers(@RequestBody @Valid CreateBillUserCommand command,
                                                       @PathVariable Long billId,
                                                       @ApiIgnore UserRequestContext context) {
        return billUserService.save(context, command, billId)
                .map(AppError::new, Function.identity())
                .fold(ResponseEntity.status(HttpStatus.UNAUTHORIZED)::body, ResponseEntity::ok);
    }

    @DeleteMapping("/bills/{billId}/users/{billUserId}")
    public ResponseEntity<?> deleteBillUser(@PathVariable long billId,
                                               @PathVariable long billUserId,
                                               @ApiIgnore UserRequestContext context) {
        return billUserService.delete(context, billUserId)
                .map(AppError::new, Function.identity())
                .fold(ResponseEntity.status(HttpStatus.UNAUTHORIZED)::body, ResponseEntity::ok);
    }
}
