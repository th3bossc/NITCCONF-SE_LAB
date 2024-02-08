package com.nitconfbackend.nitconf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.io.IOException;


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
import org.springframework.mock.web.MockMultipartFile;
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
import com.nitconfbackend.nitconf.service.DocumentUtility;


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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNewSession_ValidRequest() {

        SecurityContext securityContext = mock(SecurityContext.class);
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
        mockUser.sessions = new ArrayList<Session>();

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

        SecurityContext securityContext = mock(SecurityContext.class);
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
        mockUser.sessions = new ArrayList<Session>();

        Tag mockTag = new Tag("Java");
        List<Tag> mockTags = new ArrayList<>();
        mockTags.add(mockTag);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(tagsRepository.findById(anyString())).thenReturn(Optional.of(mockTag));

        assertEquals(HttpStatus.BAD_REQUEST, sessionController.newSession(request).getStatusCode());
    }

    @Test
    public void testUpdateSession_ValidRequest() {

        SecurityContext securityContext = mock(SecurityContext.class);
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

    @Test
    public void testUpdateSession_WrongSessionId() {
        // Mock behavior of sessionRepository to return an empty Optional when findById
        // is called with invalid session ID
        when(sessionRepository.findById(anyString())).thenReturn(Optional.empty());

        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.setTitle("Updated Title");
        sessionRequest.setDescription("Updated Description");
        sessionRequest.setLanguage("English");
        sessionRequest.setLevel(Level.INTERMEDIATE);
        sessionRequest.setStatus(Status.PENDING);
        sessionRequest.setTags(Arrays.asList("Tag1", "Tag2", "Tag3"));

        assertThrows(NoSuchElementException.class,
                () -> sessionController.updateSession("wrongSessionId", sessionRequest));
    }

    @Test
    public void testGetAllSessions() {
        String userEmail = "test@example.com";

        User user = mock(User.class);
        user.setEmail(userEmail);

        List<Session> sessions = Arrays.asList(new Session(), new Session());

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
        assertEquals(2, responseEntity.getBody().size());

    }

    @Test
    public void testGetAllSessions_WrongEmail() {
        String wrongEmail = "wrong@example.com";

        when(userRepository.findByEmail(wrongEmail)).thenReturn(Optional.empty());

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(wrongEmail);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(NoSuchElementException.class,
        () -> sessionController.getAllSessions());
    }

    @Test
    public void testGetSession_ValidId() {
        String validId = "validId";
        Session expectedSession = new Session(); 
        when(sessionRepository.findById(validId)).thenReturn(Optional.of(expectedSession));
        ResponseEntity<Session> responseEntity = sessionController.getSession(validId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedSession, responseEntity.getBody());
    }

    @Test
    public void testGetSession_NullId() {
       
        ResponseEntity<Session> responseEntity = sessionController.getSession(null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetSession_WrongId() {
        String wrongId = "wrongId";
        when(sessionRepository.findById(wrongId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()->sessionController.getSession(wrongId));

    }

    @Test
    public void testUpdateStatusToAccepted_ValidId() {
        String validId = "validId";
        Session session = new Session(); 
        when(sessionRepository.findById(validId)).thenReturn(Optional.of(session));
        ResponseEntity<String> responseEntity = sessionController.updateStatusToAccepted(validId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("UPDATED STATUS TO ACCEPTED", responseEntity.getBody());
    }

    @Test
    public void testUpdateStatusToAccepted_NullId() {
        ResponseEntity<String> responseEntity = sessionController.updateStatusToAccepted(null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateStatusToAccepted_InvalidId() {
        String invalidId = "invalidId";

        when(sessionRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()->sessionController.updateStatusToAccepted(invalidId));
    }

    @Test
    public void testUpdateStatusToRejected_ValidId() {
        String validId = "validId";
        Session session = new Session(); 
        when(sessionRepository.findById(validId)).thenReturn(Optional.of(session));
        ResponseEntity<String> responseEntity = sessionController.updateStatusToRejected(validId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("UPDATED STATUS TO REJECTED", responseEntity.getBody());
    }

    @Test
    public void testUpdateStatusToRejected_NullId() {
        ResponseEntity<String> responseEntity = sessionController.updateStatusToRejected(null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateStatusToRejected_InvalidId() {
        String invalidId = "invalidId";

        when(sessionRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()->sessionController.updateStatusToRejected(invalidId));
    }

    @Test
    public void testDeleteSession_ValidId() {
        String validId = "validId";
        Session session = new Session();
        when(sessionRepository.findById(validId)).thenReturn(Optional.of(session));

        ResponseEntity<String> responseEntity = sessionController.deleteSession(validId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("DELETED SESSION", responseEntity.getBody());
        verify(sessionRepository, times(1)).delete(session);
    }

    @Test
    public void testDeleteSession_NullId() {
        ResponseEntity<String> responseEntity = sessionController.deleteSession(null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteSession_InvalidId() {
        String invalidId = "invalidId";

        when(sessionRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()->sessionController.deleteSession(invalidId));

    }
    
    // @Test
    // public void testUploadPdf_nullFile_FileUploaded() throws IOException {
    //     String validId = "validId";
    //     byte[] fileData = "Sample PDF content".getBytes();
    //     MockMultipartFile mockFile = new MockMultipartFile("file", "test.pdf", "application/pdf", fileData);
    //     Session session = new Session(); 
    //     DocumentUtility documentUtility = mock(DocumentUtility.class);

    //     // Mock sessionRepository behavior to return the session when findById is called with the valid ID
    //     when(sessionRepository.findById(validId)).thenReturn(Optional.of(session));
    //     // Mock documentUtility behavior to return the byte array of the file
    //     when(documentUtility.pdfToByte(mockFile)).thenReturn(fileData);


    //     ResponseEntity<?> responseEntity = sessionController.uploadPdf(validId, mockFile);
    //     assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    // }

    @Test
    public void testUploadPdf_NullSessionId() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "Sample PDF content".getBytes());

        ResponseEntity<?> responseEntity = sessionController.uploadPdf(null, mockFile);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUploadPdf_InvalidSessionId() throws IOException {
        String invalidId = "invalidId";
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "Sample PDF content".getBytes());
        DocumentUtility documentUtility = mock(DocumentUtility.class);

        when(sessionRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()->sessionController.uploadPdf(invalidId, mockFile));

    }

}
