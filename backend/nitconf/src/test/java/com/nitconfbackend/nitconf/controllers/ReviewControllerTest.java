package com.nitconfbackend.nitconf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.nitconfbackend.nitconf.models.DocumentVersion;
import com.nitconfbackend.nitconf.models.Review;
import com.nitconfbackend.nitconf.repositories.DocumentVersionRepository;
import com.nitconfbackend.nitconf.repositories.ReviewRepository;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.types.ReviewRequest;

public class ReviewControllerTest {

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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetMethodName() {
       
        String docId = "1";
        List<Review> reviews = new ArrayList<>();
      
        DocumentVersion documentVersion = new DocumentVersion();
        documentVersion.setReviews(reviews);

       
        when(documentVersionRepository.findById(docId)).thenReturn(Optional.of(documentVersion));

       
        ResponseEntity<List<Review>> responseEntity = reviewController.getMethodName(docId);

       
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetDocReviews_NullId() {
       
        String nullId = null;
        when(documentVersionRepository.findById(nullId)).thenReturn(Optional.empty());

        ResponseEntity<List<Review>> responseEntity = reviewController.getMethodName(nullId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetMethodName_WrongDocId() {
        String wrongDocId = "WongID";
        when(documentVersionRepository.findById(wrongDocId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            reviewController.getMethodName(wrongDocId);
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
            reviewController.getMethodName(nonExistingReviewId);
        });
    }
    
}
