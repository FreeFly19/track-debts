package com.freefly19.trackdebts.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaEmailSender;

    @Async
    public void sendNotification(Email mail) {
        MimeMessage email = javaEmailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(email, true);

            helper.setFrom(mail.getSender());
            helper.setTo(mail.getReceiver());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true);

            javaEmailSender.send(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
