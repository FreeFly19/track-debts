package com.freefly19.trackdebts.eventhandlers;

import com.freefly19.trackdebts.bill.user.BillUser;
import com.freefly19.trackdebts.bill.user.BillUserCreatedEvent;
import com.freefly19.trackdebts.bill.user.BillUserRepository;
import com.freefly19.trackdebts.notification.Email;
import com.freefly19.trackdebts.notification.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor
@Component
public class BillUserCreatedEmailGeneratorHandler {
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final BillUserRepository repository;

    @EventListener
    public void onBillUserCreatedEvent(BillUserCreatedEvent event) {
        BillUser billUser = repository.getOne(event.getBillUserId());
        Email email = Email.builder()
                .sender("no-reply@track-debts.com")
                .receiver(billUser.getUser().getEmail())
                .subject("You've been added to " + billUser.getBill().getTitle() + " bill")
                .body(templateForBillUserEmail(billUser))
                .build();
        emailService.sendNotification(email);
    }

    private String templateForBillUserEmail(BillUser billUser) {
        Context context = new Context();
        context.setVariable("USER", billUser.getUser().getDisplayName());
        context.setVariable("BILL", billUser.getBill().getTitle());

        return templateEngine.process("email", context);
    }
}
