package com.freefly19.trackdebts.bill.billrecognition;

import com.freefly19.trackdebts.AppError;
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
    public ResponseEntity<?> BillRecognition(@RequestBody @Valid ReceiveImageCommand command) {
        return billRecognitionService.callHttpClient(command)
                .map(AppError::new)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(ResponseEntity.ok()::build);
    }
}
