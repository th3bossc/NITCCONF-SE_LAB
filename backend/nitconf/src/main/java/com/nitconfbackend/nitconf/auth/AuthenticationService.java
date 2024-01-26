package com.nitconfbackend.nitconf.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nitconfbackend.nitconf.RequestTypes.RegisterRequest;
import com.nitconfbackend.nitconf.config.JwtService;
import com.nitconfbackend.nitconf.models.Role;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;

// import com.example.demo.config.JwtService;
// import com.example.demo.models.Role;
// import com.example.demo.models.User;
// import com.example.demo.respository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(encoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
        if (user != null)
            repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), 
                request.getPassword()
            )
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }
}