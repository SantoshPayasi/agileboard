package com.spdev.agileboard_backend.Implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.spdev.agileboard_backend.services.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void SendEmailWIthToken(String userEmail, String link) throws Exception {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String subject = "Join Project Team Invitation";
            String text = "Click The Link to Join Project Team: " + link;
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setTo(userEmail);
            helper.setFrom("AgileBoard");

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            if (e instanceof MailSendException) {
                throw new MailSendException(e.getMessage());
            }
            throw new InternalError(e.getMessage());
        }
    }

}
