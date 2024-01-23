package com.nitconfbackend.nitconf.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitconfbackend.nitconf.auth.AuthenticationRequest;
import com.nitconfbackend.nitconf.auth.AuthenticationResponse;
import com.nitconfbackend.nitconf.auth.AuthenticationService;
import com.nitconfbackend.nitconf.auth.RegisterRequest;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService service;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest user) {
        if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null || user.getPhoneNumber() == null)
            return ResponseEntity.badRequest().build();
        Optional<User> userExists = userRepo.findByEmail(user.getEmail());
        System.out.print(userExists);
        if (userExists.isPresent())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.register(user));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest user) {
        if (user.getEmail() == null || user.getPassword() == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.login(user));
    }

}

