package com.freefly19.trackdebts.bill.item.participant;

import com.freefly19.trackdebts.security.UserRequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ItemParticipantController {
    private final ItemParticipantService itemParticipantService;

    @PutMapping("/bills/{billId}/items/{itemId}/participate")
    public ResponseEntity<?> eatenAmount(@PathVariable long billId,
                                         @PathVariable long itemId,
                                         @Valid @RequestBody ItemParticipantCommand command,
                                         UserRequestContext context) {

        return itemParticipantService.specifyCoefficient(itemId, command, context)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(ResponseEntity.ok()::build);
    }
}
