package com.nitconfbackend.nitconf.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.service.EmailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private UserRepository userRepo;

    private EmailSender emailSender;

    @PutMapping("/verify/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setIsVerified(true);
        userRepo.save(user);
        return ResponseEntity.ok("Email verified successfully");
    }

    @PutMapping("/resend")
    public ResponseEntity<String> resendEmail() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        emailSender.sendEmail("NITCONF", user.getEmail(), "verify email", "click the below link to verify your email \n http://localhost:8080/email/verify/" + user.getId());
        return ResponseEntity.ok("Email sent successfully");
    }


    @PutMapping("/reset/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token) {
        //TODO: process PUT request
        
        return ResponseEntity.ok("Password reset successfully");
    }
}
