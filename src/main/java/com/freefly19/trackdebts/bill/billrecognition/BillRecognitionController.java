package com.freefly19.trackdebts.bill.billrecognition;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BillRecognitionController {
    private final BillRecognitionService billRecognitionService;

    @PostMapping("/bill-recognition")
    public ResponseEntity<Void> BillRecognition(@RequestBody @Valid ReceiveImageCommand command) {
        billRecognitionService.callHttpClient(command);
        return ResponseEntity.ok().build();
    }
}
