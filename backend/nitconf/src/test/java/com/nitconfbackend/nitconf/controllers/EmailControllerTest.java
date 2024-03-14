package com.nitconfbackend.nitconf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nitconfbackend.nitconf.models.Role;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.service.EmailSender;
import com.nitconfbackend.nitconf.service.JwtService;
import com.nitconfbackend.nitconf.types.ResendRequest;
import com.nitconfbackend.nitconf.types.ResetRequest;

public class EmailControllerTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private EmailController emailController;

    private User sampleUser;

    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.sampleUser = new User(
                "test_id",
                "testFirstName",
                "testLastName",
                "testEmail",
                "testPhoneNumber",
                "testPassword",
                Role.USER,
                false,
                new ArrayList<>());
        this.token = jwtService.generateToken(sampleUser);
    }

    @Test
    void testResendEmail() {
        ResendRequest body = new ResendRequest("testEmail");

        doAnswer((i) -> null).when(emailSender).sendEmail(anyString(), anyString(), anyString(), anyString());
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(sampleUser));

        ResponseEntity<String> response = emailController.resendEmail(body);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testResetEmail() {
        ResendRequest body = new ResendRequest("testEmail");
        doAnswer((i) -> null).when(emailSender).sendEmail(anyString(), anyString(), anyString(),
                anyString());
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(sampleUser));

        ResponseEntity<String> response = emailController.resetEmail(body);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void testResetPassword() {
        ResetRequest body = new ResetRequest("newPassword");

        doAnswer((i) -> null).when(emailSender).sendEmail(anyString(), anyString(), anyString(), anyString());
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(sampleUser));
        when(userRepo.save(any(User.class))).thenReturn(sampleUser);
        when(encoder.encode(anyString())).thenReturn("newPassword");
        when(jwtService.extractUsername(token)).thenReturn(sampleUser.email);

        ResponseEntity<String> response = emailController.resetPassword(token, body);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testResetPassword_invalidUser() {
        ResetRequest body = new ResetRequest("newPassword");

        doAnswer((i) -> {
            return null;
        }).when(emailSender).sendEmail(anyString(), anyString(), anyString(),
                anyString());

        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepo.save(any(User.class))).thenReturn(sampleUser);
        when(encoder.encode(anyString())).thenReturn("newPassword");

        assertThrows(NoSuchElementException.class, () -> {
            emailController.resetPassword(token, body);
        });

    }

    @Test
    void testResetPassword_expiredToken() {
        ResetRequest body = new ResetRequest("newPassword");

        doAnswer(i -> null).when(emailSender).sendEmail(anyString(), anyString(), anyString(), anyString());

        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepo.save(any(User.class))).thenReturn(sampleUser);
        when(encoder.encode(anyString())).thenReturn("newPassword");
        when(jwtService.isTokenExpired(token)).thenReturn(true);

        ResponseEntity<String> response = emailController.resetPassword(token, body);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testVerifyEmail() {
        when(userRepo.findByEmail(sampleUser.email)).thenReturn(Optional.of(sampleUser));
        when(userRepo.save(any(User.class))).thenReturn(sampleUser);
        when(jwtService.extractUsername(token)).thenReturn(sampleUser.email);
        ResponseEntity<String> response = emailController.verifyEmail(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testVerifyEmail_invalidUser() {
        when(userRepo.findByEmail(sampleUser.email)).thenReturn(Optional.empty());
        when(userRepo.save(any(User.class))).thenReturn(sampleUser);
        when(jwtService.extractUsername(anyString())).thenReturn(sampleUser.email);

        assertThrows(NoSuchElementException.class, () -> {
            emailController.verifyEmail(token);
        });
    }
}
