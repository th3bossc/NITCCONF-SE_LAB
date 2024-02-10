package com.nitconfbackend.nitconf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * EmailSender
 * Service for sending email notifications
 * 
 * @version 1.0
 * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
 */
@Service
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * emailTemplate
     * wraps around given input string and creates a template of the
     * mail to be sent
     * 
     * @param name - name of the receiver
     * @param body - body of the email
     * @return a string that wraps around the body of the email
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    private final String emailTemplate(String name, String body) {
        return "Hello " + name + ",\n\n" + body + "\n\nRegards,\nNitConf Team";
    }

    /**
     * sendEmail
     * sends email to the receiver with the given inputs
     * 
     * @param senderName - name of the sender (NITCONF)
     * @param toEmail    - email id of the receiver
     * @param subject    - subject of the mail
     * @param body       - body of the mail
     * @see #emailTemplate(String, String)
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    public void sendEmail(String senderName, String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sadderbot@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(emailTemplate(senderName, body));
        mailSender.send(message);
    }
}
