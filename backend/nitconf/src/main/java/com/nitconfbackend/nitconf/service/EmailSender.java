package com.nitconfbackend.nitconf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    
    @Autowired
    private JavaMailSender mailSender;

    private final String emailTemplate(String name, String body) {
        return "Hello " + name + ",\n\n" + body + "\n\nRegards,\nNitConf Team";
    }

    public void sendEmail(String senderName, String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sadderbot@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(emailTemplate(senderName, body));
        mailSender.send(message);
    }
}
