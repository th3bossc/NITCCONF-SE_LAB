package com.nitconfbackend.nitconf.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.NoSuchElementException;
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
    public void testNewSession_nullLanguage() {

        SecurityContext securityContext=mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        SessionRequest request = new SessionRequest();
        request.setTitle("Test Title");
        request.setDescription("Test Description");
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

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(tagsRepository.findById(anyString())).thenReturn(Optional.of(mockTag));

        assertEquals(HttpStatus.BAD_REQUEST, sessionController.newSession(request).getStatusCode());
    }

    @Test
    public void testNewSession_Invalidtag() {

        SecurityContext securityContext=mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        SessionRequest request = new SessionRequest();
        request.setTitle("Test Title");
        request.setDescription("Test Description");
        request.setLevel(Level.INTERMEDIATE);
        request.setStatus(Status.PENDING);
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        request.setTags(tags);

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.sessions=new ArrayList<Session>();


        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        ResponseEntity<Session> responseEntity = sessionController.newSession(request);

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

        when(sessionRepository.findById(anyString())).thenReturn(Optional.of(mockSession));
        when(tagsRepository.findById(anyString())).thenReturn(Optional.of(mockTag));
        when(sessionRepository.save(any(Session.class))).thenReturn(mockSession);

        ResponseEntity<Session> responseEntity = sessionController.updateSession(sessionId, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Updated Title", responseEntity.getBody().getTitle());
        assertEquals("Updated Description", responseEntity.getBody().getDescription());
        assertEquals("Updated Language", responseEntity.getBody().getLanguage());
    }

    // @Test
    // public void testUpdateSession_WrongSessionId() {
    //     when(sessionRepository.findById(anyString())).thenReturn(Optional.empty());

    //     SessionRequest sessionRequest = new SessionRequest();
    //     sessionRequest.setTitle("Updated Title");
    //     sessionRequest.setDescription("Updated Description");
    //     sessionRequest.setLanguage("English");
    //     sessionRequest.setLevel(Level.INTERMEDIATE);
    //     sessionRequest.setStatus(Status.PENDING);
    //     sessionRequest.setTags(Arrays.asList("Tag1", "Tag2", "Tag3"));

    //     // Call the updateSession method with an invalid session ID
    //     ResponseEntity<Session> responseEntity = sessionController.updateSession("wrongSessionId", sessionRequest);
    //     assertThrows(Exception.class, () -> {
    //         SessionController.updateSession("wrongSessionId", sessionRequest);
    //     });
    // }

    @Test
public void testUpdateSession_WrongSessionId() {
    // Mock behavior of sessionRepository to return an empty Optional when findById is called with invalid session ID
    when(sessionRepository.findById(anyString())).thenReturn(Optional.empty());

    SessionRequest sessionRequest = new SessionRequest();
    sessionRequest.setTitle("Updated Title");
    sessionRequest.setDescription("Updated Description");
    sessionRequest.setLanguage("English");
    sessionRequest.setLevel(Level.INTERMEDIATE);
    sessionRequest.setStatus(Status.PENDING);
    sessionRequest.setTags(Arrays.asList("Tag1", "Tag2", "Tag3"));

    // Call the updateSession method with an invalid session ID and assert that it throws an exception
    assertThrows(NoSuchElementException.class, () -> {
        sessionController.updateSession("wrongSessionId", sessionRequest);
    });
}
@Test
    public void testGetSession_ValidSessionId() {
        String sessionId = "session123";

        Session session = new Session();
        session.setId(sessionId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        ResponseEntity<Session> responseEntity = sessionController.getSession(sessionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(session, responseEntity.getBody());
    }

    @Test
    public void testGetSession_InvalidSessionId() {
        String invalidSessionId = "invalidSessionId";

        when(sessionRepository.findById(invalidSessionId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            sessionController.getSession(invalidSessionId);
        });
    }

    @Test
    public void testGetAllSessions() {
        String userEmail = "test@example.com";

        User user = mock(User.class);
        user.setEmail(userEmail);

        List<Session> sessions = Arrays.asList(new Session(), new Session());

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(userEmail);
        SecurityContextHolder.setContext(securityContext);

        when(user.getSessions()).thenReturn(sessions);
        ResponseEntity<List<Session>> responseEntity = sessionController.getAllSessions();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sessions, responseEntity.getBody());
    }
}
