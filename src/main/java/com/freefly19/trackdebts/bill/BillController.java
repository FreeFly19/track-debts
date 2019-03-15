package com.freefly19.trackdebts.bill;

import com.freefly19.trackdebts.AppError;
import com.freefly19.trackdebts.security.UserRequestContext;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@RestController
public class BillController {
    private final BillService billService;

    @GetMapping("/bills")
    public Page<BillDto> bills(Pageable pageable, @ApiIgnore UserRequestContext context) {
        return billService.findAll(pageable, context);
    }

    @PostMapping("/bills")
    public BillDto createBill(@RequestBody CreateUpdateBillCommand command, @ApiIgnore UserRequestContext context) {
        return billService.save(command, context);
    }

    @ApiOperation(value = "Create Bill", response = BillDto.class)
    @GetMapping("/bills/{id}")
    public ResponseEntity<?> createBill(@PathVariable long id, @ApiIgnore UserRequestContext context) {
        return billService.get(id, context)
                .map(AppError::new, Function.identity())
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }

    @ApiOperation(value = "Lock Bill", response = BillDto.class)
    @PutMapping("/bills/{billId}/lock")
    public ResponseEntity<?> lockBill(@PathVariable long billId, @ApiIgnore UserRequestContext context) {
        return billService.lock(billId, context)
                .map(AppError::new, Function.identity())
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }

    @GetMapping("/bills/autocompleteRestaurant")
    public ResponseEntity<List<BillDto>> autocompleteRestaurant(@RequestParam String restaurant) {
        return ResponseEntity.ok(billService.search(restaurant));
    }
}
