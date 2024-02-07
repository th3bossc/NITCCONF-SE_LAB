package com.nitconfbackend.nitconf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;

import com.nitconfbackend.nitconf.models.DocumentVersion;
import com.nitconfbackend.nitconf.models.Review;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.models.Role;

import com.nitconfbackend.nitconf.repositories.DocumentVersionRepository;
import com.nitconfbackend.nitconf.repositories.ReviewRepository;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.types.ReviewRequest;

public class ReviewControllerTest {

    @Mock
    private Authentication authentication;

    @Mock
    private DocumentVersionRepository documentVersionRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetReviewsDoc() {

        String docId = "1";
        List<Review> reviews = new ArrayList<>();

        DocumentVersion documentVersion = new DocumentVersion();
        documentVersion.setReviews(reviews);

        when(documentVersionRepository.findById(docId)).thenReturn(Optional.of(documentVersion));

        ResponseEntity<List<Review>> responseEntity = reviewController.getReviews(docId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetReviewsDoc_NullId() {

        String nullId = null;
        when(documentVersionRepository.findById(nullId)).thenReturn(Optional.empty());

        ResponseEntity<List<Review>> responseEntity = reviewController.getReviews(nullId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetReviewsDoc_WrongDocId() {
        String wrongDocId = "WongID";
        when(documentVersionRepository.findById(wrongDocId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            reviewController.getReviews(wrongDocId);
        });
    }

    @Test
    public void testGetReview_ValidId() {
        String reviewId = "1";
        Review expectedReview = new Review();
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(expectedReview));

        ResponseEntity<Review> responseEntity = reviewController.getReview(reviewId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedReview, responseEntity.getBody());
    }

    @Test
    public void testGetReview_NullId() {
        ResponseEntity<Review> responseEntity = reviewController.getReview(null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetReview_NonExistingId() {
        String nonExistingReviewId = "nonExistingId";
        when(reviewRepository.findById(nonExistingReviewId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            reviewController.getReview(nonExistingReviewId);
        });
    }

    @Test
    public void testCreateReview() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");

        String documentId = "1";
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setComment("Test comment");

        DocumentVersion documentVersion = new DocumentVersion();
        documentVersion.setId(documentId);
        documentVersion.setReviews(new ArrayList<Review>());

        User user = new User();
        user.setRole(Role.REVIEWER);

        when(reviewRepository.save(any(Review.class))).thenReturn(new Review());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(documentVersionRepository.findById(anyString())).thenReturn(Optional.of(documentVersion)); // Mock to
                                                                                                        // return an
                                                                                                        // Optional
                                                                                                        // containing a
                                                                                                        // DocumentVersion
                                                                                                        // object

        ResponseEntity<String> responseEntity = reviewController.createReview(reviewRequest, documentId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Review created", responseEntity.getBody());
    }

    @Test

    public void testCreateReview_InvalidRole() {

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");

        String documentId = "1";
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setComment("Test comment");

        User user = new User();
        user.setRole(Role.USER);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertEquals(HttpStatus.BAD_REQUEST, reviewController.createReview(reviewRequest, documentId).getStatusCode());

    }

}
