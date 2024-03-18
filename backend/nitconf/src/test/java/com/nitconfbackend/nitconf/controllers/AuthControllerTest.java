package com.nitconfbackend.nitconf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.service.AuthenticationService;
import com.nitconfbackend.nitconf.types.AuthenticationRequest;
import com.nitconfbackend.nitconf.types.AuthenticationResponse;
import com.nitconfbackend.nitconf.types.RegisterRequest;

public class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        AuthenticationRequest user = new AuthenticationRequest("john@doe.com", "samplePassword");
        when(authenticationService.login(user)).thenReturn(new AuthenticationResponse("sample_token"));

        ResponseEntity<AuthenticationResponse> response = authController.login(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void testLogin_nullEmail() {
        AuthenticationRequest user = new AuthenticationRequest(null, "samplePassword");

        ResponseEntity<AuthenticationResponse> response = authController.login(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testRegisterUser_CorrectInputs() {
        RegisterRequest user = new RegisterRequest("John", "Doe", "john@doe.com",
                "samplePassword", "1234567890");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        doReturn(new AuthenticationResponse("token")).when(authenticationService).register(user);

        ResponseEntity<AuthenticationResponse> response = authController.registerUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody().getToken());
    }

    @Test
    void testRegisterUser_MissingFirstName() {
        RegisterRequest user = new RegisterRequest(null, "Doe", "john@doe.com", "samplePassword", "1234567890");
        when(authenticationService.register(user)).thenReturn(new AuthenticationResponse("sample_token"));

        ResponseEntity<AuthenticationResponse> response = authController.registerUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testRegisterUser_UserExistsInDB() {
        RegisterRequest user = new RegisterRequest("John", "Doe", "john@doe.com",
                "samplePassword", "1234567890");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        when(authenticationService.register(user)).thenReturn(new AuthenticationResponse("sample_token"));

        ResponseEntity<AuthenticationResponse> response = authController.registerUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
