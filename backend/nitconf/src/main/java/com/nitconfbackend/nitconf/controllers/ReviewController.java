package com.nitconfbackend.nitconf.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nitconfbackend.nitconf.models.DocumentVersion;
import com.nitconfbackend.nitconf.models.Review;
import com.nitconfbackend.nitconf.models.Role;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.DocumentVersionRepository;
import com.nitconfbackend.nitconf.repositories.ReviewRepository;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.types.ReviewRequest;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController

@RequestMapping("/api/review")
public class ReviewController{

    @Autowired
    private DocumentVersionRepository docRepo;

    @Autowired
    private ReviewRepository revRepo;

    @Autowired
    private UserRepository userRepo;


    @GetMapping("/doc/{id}")
    public ResponseEntity<List<Review>> GetReviewsDoc(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
            DocumentVersion doc = docRepo.findById(id).orElseThrow();

            //Optional<List<Review>> reviewsOptional = Optional.ofNullable(docRepo.findById(id).orElseThrow().getReviews());

            return ResponseEntity.ok(doc.getReviews());
        }
    

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Review review = revRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(review);
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<String> createReview(@RequestBody ReviewRequest body, @PathVariable String id) {
        Review review = new Review();
        review.setComment(body.comment);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User profile = userRepo.findByEmail(email).orElseThrow();
        if (id == null)
            return ResponseEntity.notFound().build();
        if (profile.role != Role.REVIEWER && profile.role != Role.PROGRAM_COMMITTEE) {
            return ResponseEntity.badRequest().build();
        }
        DocumentVersion targetDoc = docRepo.findById(id).orElseThrow() ;
        // review.doc=docu;
        review.setReviewer(profile);
        revRepo.save(review);
        targetDoc.getReviews().add(review);
        docRepo.save(targetDoc);
        return ResponseEntity.ok("Review created");
    }
    
    
}