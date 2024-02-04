package com.nitconfbackend.nitconf.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nitconfbackend.nitconf.models.Session;
import com.nitconfbackend.nitconf.models.Status;
import com.nitconfbackend.nitconf.models.Tag;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.models.Level;

import com.nitconfbackend.nitconf.repositories.SessionRepository;
import com.nitconfbackend.nitconf.repositories.TagsRepository;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.types.SessionRequest;

import lombok.experimental.PackagePrivate;

public class SessionControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private TagsRepository tagsRepository;

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNewSession_ValidRequest() {

        SecurityContext securityContext=mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        SessionRequest request = new SessionRequest();
        request.setTitle("Test Title");
        request.setDescription("Test Description");
        request.setLanguage("English");
        request.setLevel(Level.INTERMEDIATE);
        request.setStatus(Status.PENDING);
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        request.setTags(tags);

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.sessions=new ArrayList<Session>();

        Tag mockTag = new Tag("Java");
        List<Tag> mockTags = new ArrayList<>();
        mockTags.add(mockTag);

        // Mock repository behavior
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(tagsRepository.findById(anyString())).thenReturn(Optional.of(mockTag));
        when(sessionRepository.save(any(Session.class))).thenReturn(new Session());

        // Test
        ResponseEntity<Session> responseEntity = sessionController.newSession(request);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateSession_ValidRequest() {

        SecurityContext securityContext=mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        
        String sessionId = "1";
        SessionRequest request = new SessionRequest();
        request.setTitle("Updated Title");
        request.setDescription("Updated Description");
        request.setLanguage("Updated Language");
        request.setLevel(Level.ADVANCED);
        request.setStatus(Status.PENDING);
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        request.setTags(tags);

        Session mockSession = new Session();
        mockSession.setId(sessionId);

        Tag mockTag = new Tag("Java");
        List<Tag> mockTags = new ArrayList<>();
        mockTags.add(mockTag);

        // Mock repository behavior
        when(sessionRepository.findById(anyString())).thenReturn(Optional.of(mockSession));
        when(tagsRepository.findById(anyString())).thenReturn(Optional.of(mockTag));
        when(sessionRepository.save(any(Session.class))).thenReturn(mockSession);

        // Test
        ResponseEntity<Session> responseEntity = sessionController.updateSession(sessionId, request);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Updated Title", responseEntity.getBody().getTitle());
        assertEquals("Updated Description", responseEntity.getBody().getDescription());
        assertEquals("Updated Language", responseEntity.getBody().getLanguage());


    }

    // Add more test cases for other methods as needed
}
