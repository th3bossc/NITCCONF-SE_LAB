package com.nitconfbackend.nitconf.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.service.EmailSender;
import com.nitconfbackend.nitconf.service.JwtService;
import com.nitconfbackend.nitconf.types.ResetRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private UserRepository userRepo;

    private JwtService jwtService;
    private EmailSender emailSender;
    private PasswordEncoder encoder;

    @PutMapping("/verify/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token) {
        String email = jwtService.extractUsername(token);
        User user = userRepo.findByEmail(email).orElseThrow();
        user.setIsVerified(true);
        userRepo.save(user);
        return ResponseEntity.ok("Email verified successfully");
    }

    @PutMapping("/resend")
    public ResponseEntity<String> resendEmail() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String newToken = jwtService.generateToken(user);
        emailSender.sendEmail("NITCONF", user.getEmail(), "verify email",
                "click the below link to verify your email \n http://localhost:3000/email/verify/" + newToken);
        return ResponseEntity.ok("Email sent successfully");
    }

    @GetMapping("/reset")
    public ResponseEntity<String> resetEmail(@RequestBody String email) {
        User user = userRepo.findByEmail(email).orElseThrow();
        String token = jwtService.generateToken(user);
        emailSender.sendEmail("NITCONF", user.getEmail(), "reset password",
                "click the below link to reset your password \n http://localhost:3000/email/reset/" + token);
        return ResponseEntity.ok("Email sent successfully");
    }

    @PutMapping("/reset/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody ResetRequest body) {
        if (jwtService.isTokenExpired(token))
            return ResponseEntity.badRequest().body("Token expired");
        String email = jwtService.extractUsername(token);
        User user = userRepo.findByEmail(email).orElseThrow();
        user.setPassword(encoder.encode(body.password));
        return ResponseEntity.ok("Password reset successfully");
    }
}
