package com.freefly19.trackdebts.bill.user;

import com.freefly19.trackdebts.security.UserRequestContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class BillUserController {
    private final BillUserService billUserService;

    @GetMapping("bills/{billId}/users")
    public ResponseEntity<List<BillUserDto>> findAll(@PathVariable long billId) {
        return ResponseEntity.ok(billUserService.findByBillId(billId));
    }

    @PutMapping("/bills/{billId}/users")
    public ResponseEntity<BillUserDto> createBillUsers(@RequestBody @Valid CreateBillUserCommand command,
                                                       @PathVariable Long billId,
                                                       @ApiIgnore UserRequestContext context) {
        return billUserService.save(context, command, billId)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.status(HttpStatus.UNAUTHORIZED)::build);
    }

    @DeleteMapping("/bills/{billId}/users/{billUserId}")
    public ResponseEntity<Void> deleteBillUser(@PathVariable long billId,
                                               @PathVariable long billUserId,
                                               @ApiIgnore UserRequestContext context) {
        return billUserService.delete(context, billUserId) ?
                ResponseEntity.ok().build():
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
