package com.nitconfbackend.nitconf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.nitconfbackend.nitconf.models.Role;
import com.nitconfbackend.nitconf.models.User;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private User sampleUser;

    private String token;

    @BeforeEach
    public void setUp() {
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
    public void testUsername() {
        String username = jwtService.extractUsername(token);
        assertEquals(sampleUser.email, username);
    }

    @Test
    public void testExpired() {
        assertFalse(jwtService.extractExpiration(token).before(new Date()));
    }
}
