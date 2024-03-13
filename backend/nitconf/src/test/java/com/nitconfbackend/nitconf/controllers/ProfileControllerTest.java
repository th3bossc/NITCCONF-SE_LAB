package com.nitconfbackend.nitconf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nitconfbackend.nitconf.models.Role;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.types.ProfileRequest;

public class ProfileControllerTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testGetUser() {
        String validId = "test_id";
        User sampleUser = new User(
                "test_id",
                "testFirstName",
                "testLastName",
                "testEmail",
                "testPhoneNumber",
                "testPassword",
                Role.REVIEWER,
                true,
                new ArrayList<>());
        when(authentication.getName()).thenReturn(sampleUser.email);
        when(userRepo.findByEmail(sampleUser.email)).thenReturn(Optional.of(sampleUser));
        when(userRepo.findById(sampleUser.id)).thenReturn(Optional.of(sampleUser));

        ResponseEntity<User> response = profileController.getUser(validId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetUser_invalidId() {
        String invalidId = "invalid_id";
        User sampleUser = new User(
                "test_id",
                "testFirstName",
                "testLastName",
                "testEmail",
                "testPhoneNumber",
                "testPassword",
                Role.REVIEWER,
                true,
                new ArrayList<>());
        when(authentication.getName()).thenReturn("testEmail");
        when(userRepo.findByEmail("testEmail")).thenReturn(Optional.of(sampleUser));
        when(userRepo.findById(invalidId)).thenReturn(Optional.empty());

        // ResponseEntity<User> response = profileController.getUser(invalidId);

        assertThrows(NoSuchElementException.class, () -> profileController.getUser(invalidId));
    }

    @Test
    void testGetUser_invalidPerms() {
        String validId = "test_id";
        User sampleUser = new User(
                "test_id",
                "testFirstName",
                "testLastName",
                "testEmail",
                "testPhoneNumber",
                "testPassword",
                Role.USER,
                true,
                new ArrayList<>());
        when(authentication.getName()).thenReturn(sampleUser.email);
        when(userRepo.findByEmail(sampleUser.email)).thenReturn(Optional.of(sampleUser));
        when(userRepo.findById(sampleUser.id)).thenReturn(Optional.of(sampleUser));

        ResponseEntity<User> response = profileController.getUser(validId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void testProfileDetails() {
        User sampleUser = new User(
                "test_id",
                "testFirstName",
                "testLastName",
                "testEmail",
                "testPhoneNumber",
                "testPassword",
                Role.USER,
                true,
                new ArrayList<>());
        when(authentication.getPrincipal()).thenReturn(sampleUser);
        assertEquals(HttpStatus.OK, profileController.profileDetails().getStatusCode());
    }

    @Test
    void testUpdateProfile() {
        User sampleUser = new User(
                "test_id",
                "testFirstName",
                "testLastName",
                "testEmail",
                "testPhoneNumber",
                "testPassword",
                Role.USER,
                true,
                new ArrayList<>());
        ProfileRequest updatedData = new ProfileRequest(
                "updatedFirstName",
                "updatedLastName",
                "updatedPhoneNumber");
        when(authentication.getName()).thenReturn(sampleUser.email);
        when(userRepo.findByEmail(sampleUser.email)).thenReturn(Optional.of(sampleUser));
        when(authentication.getName()).thenReturn(sampleUser.email);
        when(userRepo.findByEmail(sampleUser.email)).thenReturn(Optional.of(sampleUser));
        assertEquals(HttpStatus.OK, profileController.updateProfile(updatedData).getStatusCode());
    }

    @Test
    void testUpdateProfile_invalidUser() {
        ProfileRequest updatedData = new ProfileRequest(
                "updatedFirstName",
                "updatedLastName",
                "updatedPhoneNumber");
        when(authentication.getName()).thenReturn("testEmail");
        when(userRepo.findByEmail("testEmail")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> profileController.updateProfile(updatedData));
    }

}
