package com.nitconfbackend.nitconf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.nitconfbackend.nitconf.models.Paper;
import com.nitconfbackend.nitconf.models.Status;
import com.nitconfbackend.nitconf.models.Tag;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.models.Level;

import com.nitconfbackend.nitconf.repositories.PaperRepository;
import com.nitconfbackend.nitconf.repositories.TagsRepository;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.types.PaperRequest;
import com.nitconfbackend.nitconf.service.DocumentUtility;

public class PaperControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaperRepository paperRepository;

    @Mock
    private TagsRepository tagsRepository;

    @Mock
    private DocumentUtility documentUtility;

    @InjectMocks
    private PaperController paperController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNewPaper_ValidRequest() {

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        PaperRequest request = new PaperRequest();
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
        mockUser.papers = new ArrayList<Paper>();

        Tag mockTag = new Tag("Java");
        List<Tag> mockTags = new ArrayList<>();
        mockTags.add(mockTag);

        // Mock repository behavior
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(tagsRepository.findById(anyString())).thenReturn(Optional.of(mockTag));
        when(paperRepository.save(any(Paper.class))).thenReturn(new Paper());

        // Test
        ResponseEntity<Paper> responseEntity = paperController.newPaper(request);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testNewPaper_nullLanguage() {

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        PaperRequest request = new PaperRequest();
        request.setTitle("Test Title");
        request.setDescription("Test Description");
        request.setLevel(Level.INTERMEDIATE);
        request.setStatus(Status.PENDING);
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        request.setTags(tags);

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.papers = new ArrayList<Paper>();

        Tag mockTag = new Tag("Java");
        List<Tag> mockTags = new ArrayList<>();
        mockTags.add(mockTag);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(tagsRepository.findById(anyString())).thenReturn(Optional.of(mockTag));

        assertEquals(HttpStatus.BAD_REQUEST, paperController.newPaper(request).getStatusCode());
    }

    @Test
    public void testNewPaper_invalidTags() {

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        PaperRequest request = new PaperRequest();
        request.setTitle("Test Title");
        request.setDescription("Test Description");
        request.setLevel(Level.INTERMEDIATE);
        request.setStatus(Status.PENDING);
        request.setLanguage("Malayalam");
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        request.setTags(tags);

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.papers = new ArrayList<Paper>();

        Tag mockTag = new Tag("Java");
        List<Tag> mockTags = new ArrayList<>();
        mockTags.add(mockTag);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(tagsRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> paperController.newPaper(request));
    }

    @Test
    public void testNewPaper_invalidUser() {

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        PaperRequest request = new PaperRequest();
        request.setTitle("Test Title");
        request.setDescription("Test Description");
        request.setLevel(Level.INTERMEDIATE);
        request.setStatus(Status.PENDING);
        request.setLanguage("Malayalam");
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        request.setTags(tags);

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.papers = new ArrayList<Paper>();

        Tag mockTag = new Tag("Java");
        List<Tag> mockTags = new ArrayList<>();
        mockTags.add(mockTag);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(tagsRepository.findById(anyString())).thenReturn(Optional.of(mockTag));

        assertThrows(NoSuchElementException.class, () -> paperController.newPaper(request));
    }

    @Test
    public void testUpdatePaper_ValidRequest() {

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");

        String paperId = "1";
        PaperRequest request = new PaperRequest();
        request.setTitle("Updated Title");
        request.setDescription("Updated Description");
        request.setLanguage("Updated Language");
        request.setLevel(Level.ADVANCED);
        request.setStatus(Status.PENDING);
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        request.setTags(tags);

        Paper mockPaper = new Paper();
        mockPaper.setId(paperId);

        Tag mockTag = new Tag("Java");
        List<Tag> mockTags = new ArrayList<>();
        mockTags.add(mockTag);

        when(paperRepository.findById(anyString())).thenReturn(Optional.of(mockPaper));
        when(tagsRepository.findById(anyString())).thenReturn(Optional.of(mockTag));
        when(paperRepository.save(any(Paper.class))).thenReturn(mockPaper);

        ResponseEntity<Paper> responseEntity = paperController.updatePaper(paperId, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Updated Title", responseEntity.getBody().getTitle());
        assertEquals("Updated Description", responseEntity.getBody().getDescription());
        assertEquals("Updated Language", responseEntity.getBody().getLanguage());
    }

    @Test
    public void testUpdatePaper_WrongPaperId() {
        // Mock behavior of paperRepository to return an empty Optional when findById
        // is called with invalid paper ID
        when(paperRepository.findById(anyString())).thenReturn(Optional.empty());

        PaperRequest paperRequest = new PaperRequest();
        paperRequest.setTitle("Updated Title");
        paperRequest.setDescription("Updated Description");
        paperRequest.setLanguage("English");
        paperRequest.setLevel(Level.INTERMEDIATE);
        paperRequest.setStatus(Status.PENDING);
        paperRequest.setTags(Arrays.asList("Tag1", "Tag2", "Tag3"));

        assertThrows(NoSuchElementException.class,
                () -> paperController.updatePaper("wrongPaperId", paperRequest));
    }

    @Test
    public void testUpdatePaper_emptyLanguage() {
        String paperId = "1";
        PaperRequest request = new PaperRequest();
        request.setTitle("Updated Title");
        request.setDescription("Updated Description");
        request.setLevel(Level.ADVANCED);
        request.setStatus(Status.PENDING);
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        request.setTags(tags);

        Paper mockPaper = new Paper();
        mockPaper.setId(paperId);

        Tag mockTag = new Tag("Java");
        List<Tag> mockTags = new ArrayList<>();
        mockTags.add(mockTag);

        when(paperRepository.findById(anyString())).thenReturn(Optional.of(mockPaper));
        when(tagsRepository.findById(anyString())).thenReturn(Optional.of(mockTag));

        assertEquals(HttpStatus.BAD_REQUEST, paperController.updatePaper(paperId, request).getStatusCode());
    }

    @Test
    public void testUpdatePaper_invalidTags() {
        String paperId = "1";
        PaperRequest request = new PaperRequest();
        request.setTitle("Updated Title");
        request.setDescription("Updated Description");
        request.setLanguage("Updated Language");
        request.setLevel(Level.ADVANCED);
        request.setStatus(Status.PENDING);
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        request.setTags(tags);

        Paper mockPaper = new Paper();
        mockPaper.setId(paperId);

        Tag mockTag = new Tag("Java");
        List<Tag> mockTags = new ArrayList<>();
        mockTags.add(mockTag);

        when(paperRepository.findById(anyString())).thenReturn(Optional.of(mockPaper));
        when(tagsRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> paperController.updatePaper(paperId, request));
    }

    @Test
    public void testUpdatePaper_NullId() {
       
        PaperRequest paperRequest = new PaperRequest();
        paperRequest.setTitle("Sample Title");
        paperRequest.setDescription("Sample Description");
        paperRequest.setLanguage("English");
        paperRequest.setLevel(Level.INTERMEDIATE);
        paperRequest.setStatus(Status.PENDING);
        paperRequest.setTags(new ArrayList<String>());

        // Call the updatePaper method with null id
        ResponseEntity<Paper> responseEntity = paperController.updatePaper(null, paperRequest);
        
        // Verify that ResponseEntity.notFound() is returned
        assertEquals(ResponseEntity.notFound().build(), responseEntity);
        
    }

    @Test
    public void testGetAllPapers() {
        String userEmail = "test@example.com";

        User user = mock(User.class);
        user.setEmail(userEmail);

        List<Paper> papers = Arrays.asList(new Paper(), new Paper());

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(userEmail);
        SecurityContextHolder.setContext(securityContext);

        when(user.getPapers()).thenReturn(papers);

        ResponseEntity<List<Paper>> responseEntity = paperController.getAllPapers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(papers, responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());

    }



    @Test
    public void testGetAllPapers_WrongEmail() {
        String wrongEmail = "wrong@example.com";

        when(userRepository.findByEmail(wrongEmail)).thenReturn(Optional.empty());

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(wrongEmail);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(NoSuchElementException.class,
                () -> paperController.getAllPapers());
    }

    @Test
    public void testGetPaper_ValidId() {
        String validId = "validId";
        Paper expectedPaper = new Paper();
        when(paperRepository.findById(validId)).thenReturn(Optional.of(expectedPaper));
        ResponseEntity<Paper> responseEntity = paperController.getPaper(validId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedPaper, responseEntity.getBody());
    }

    @Test
    public void testGetPaper_NullId() {

        ResponseEntity<Paper> responseEntity = paperController.getPaper(null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetPaper_WrongId() {
        String wrongId = "wrongId";
        when(paperRepository.findById(wrongId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> paperController.getPaper(wrongId));

    }

    @Test
    public void testUpdateStatusToAccepted_ValidId() {
        String validId = "validId";
        Paper paper = new Paper();
        when(paperRepository.findById(validId)).thenReturn(Optional.of(paper));
        ResponseEntity<String> responseEntity = paperController.updateStatusToAccepted(validId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("UPDATED STATUS TO ACCEPTED", responseEntity.getBody());
    }

    @Test
    public void testUpdateStatusToAccepted_NullId() {
        ResponseEntity<String> responseEntity = paperController.updateStatusToAccepted(null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateStatusToAccepted_InvalidId() {
        String invalidId = "invalidId";

        when(paperRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> paperController.updateStatusToAccepted(invalidId));
    }

    @Test
    public void testUpdateStatusToRejected_ValidId() {
        String validId = "validId";
        Paper paper = new Paper();
        when(paperRepository.findById(validId)).thenReturn(Optional.of(paper));
        ResponseEntity<String> responseEntity = paperController.updateStatusToRejected(validId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("UPDATED STATUS TO REJECTED", responseEntity.getBody());
    }

    @Test
    public void testUpdateStatusToRejected_NullId() {
        ResponseEntity<String> responseEntity = paperController.updateStatusToRejected(null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateStatusToRejected_InvalidId() {
        String invalidId = "invalidId";

        when(paperRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> paperController.updateStatusToRejected(invalidId));
    }

    @Test
    public void testGetDocument_IdNull() {
       
        ResponseEntity<?> responseEntity = paperController.getDocument(null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    public void testGetDocument_InvalidId() {
        
        String invalidId = "invalidId";
        assertThrows(NoSuchElementException.class, () -> paperController.getDocument(invalidId));
    }

    @Test
    public void testDeletePaper_ValidId() {
        String validId = "validId";
        Paper paper = new Paper();
        when(paperRepository.findById(validId)).thenReturn(Optional.of(paper));

        ResponseEntity<String> responseEntity = paperController.deletePaper(validId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("DELETED SESSION", responseEntity.getBody());
        verify(paperRepository, times(1)).delete(paper);
    }

    @Test
    public void testDeletePaper_NullId() {
        ResponseEntity<String> responseEntity = paperController.deletePaper(null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testDeletePaper_InvalidId() {
        String invalidId = "invalidId";

        when(paperRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> paperController.deletePaper(invalidId));

    }

    // @Test
    // public void testUploadPdf_nullFile_FileUploaded() throws IOException {
    // String validId = "validId";
    // byte[] fileData = "Sample PDF content".getBytes();
    // MockMultipartFile mockFile = new MockMultipartFile("file", "test.pdf",
    // "application/pdf", fileData);
    // Paper paper = new Paper();
    // DocumentUtility documentUtility = mock(DocumentUtility.class);

    // // Mock paperRepository behavior to return the paper when findById is
    // // called with the valid ID
    // when(paperRepository.findById(validId)).thenReturn(Optional.of(paper));
    // // Mock documentUtility behavior to return the byte array of the file
    // when(documentUtility.pdfToByte(mockFile)).thenReturn(fileData);

    // ResponseEntity<?> responseEntity = paperController.uploadPdf(validId,
    // mockFile);
    // assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    // }

    // @Test
    // public void testUploadPdf() throws IOException {
    // // Path path = Paths.get("src/test/java/com/nitconfbackend/trial.pdf");
    // String name = "trial.pdf";
    // String originalFileName = "trial.pdf";
    // String contentType = "application/pdf";
    // byte[] content = "Sample PDF content".getBytes();
    // String mockId = "paper_id";

    // MultipartFile multipartFile = new MockMultipartFile(name, originalFileName,
    // contentType, content);

    // Paper mockPaper = new Paper(
    // "title",
    // "description",
    // "language",
    // Level.BEGINNER,
    // Status.PENDING,
    // new ArrayList<Tag>());

    // when(paperRepository.findById(mockId)).thenReturn(Optional.of(mockPaper));
    // ResponseEntity<?> response = paperController.uploadPdf(mockId,
    // multipartFile);
    // assertEquals(HttpStatus.OK, response.getStatusCode());
    // }

    @Test
    public void testUploadPdf_NullPaperId() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.pdf", "application/pdf",
                "Sample PDF content".getBytes());

        ResponseEntity<?> responseEntity = paperController.uploadPdf(null, mockFile);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUploadPdf_InvalidPaperId() throws IOException {
        String invalidId = "invalidId";
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.pdf", "application/pdf",
                "Sample PDF content".getBytes());

        mock(DocumentUtility.class);
        when(paperRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> paperController.uploadPdf(invalidId, mockFile));

    }

}
