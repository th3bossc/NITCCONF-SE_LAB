package com.nitconfbackend.nitconf.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.service.AuthenticationService;
import com.nitconfbackend.nitconf.types.AuthenticationRequest;
import com.nitconfbackend.nitconf.types.AuthenticationResponse;
import com.nitconfbackend.nitconf.types.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService service;

    @Autowired
    private final UserRepository userRepo;

    /**
     * registerUser
     * registers a new user
     * 
     * @param user : {@link RegisterRequest}
     * @return access token : {@link AuthenticationResponse}
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest user) {
        if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null
                || user.getPassword() == null || user.getPhoneNumber() == null)
            return ResponseEntity.badRequest().build();
        Optional<User> userExists = userRepo.findByEmail(user.getEmail());
        if (userExists.isPresent())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.register(user));
    }

    /**
     * login
     * logs in a user
     * 
     * @param user : {@link AuthenticationRequest}
     * @return access token : {@link AuthenticationResponse}
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest user) {
        if (user.getEmail() == null || user.getPassword() == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.login(user));
    }

}
