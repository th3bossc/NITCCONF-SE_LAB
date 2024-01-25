package com.nitconfbackend.nitconf.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nitconfbackend.nitconf.RequestTypes.ReviewRequest;
import com.nitconfbackend.nitconf.models.DocumentVersion;
import com.nitconfbackend.nitconf.models.Review;
import com.nitconfbackend.nitconf.repositories.DocumentVersionRepository;
import com.nitconfbackend.nitconf.repositories.ReviewRepository;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController

@RequestMapping("/api/")
public class ReviewController{

    @Autowired
    private DocumentVersionRepository docRepo;

    @Autowired
    private ReviewRepository revRepo;

    @GetMapping("/reviews/{id}")
    public ResponseEntity<List<Review>> getMethodName(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        DocumentVersion doc = docRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(revRepo.findByDoc(doc));
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<Review> getReview(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Review review = revRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(review);
    }
    
    @PostMapping("/review/{id}")
    public ResponseEntity<String> createReview(@RequestBody ReviewRequest body, @PathVariable String id) {
        Review rev = new Review();
        rev.comment=body.comment;
        if (id == null)
            return ResponseEntity.notFound().build();
        DocumentVersion docu = docRepo.findById(id).orElseThrow() ;
        rev.doc=docu;
        revRepo.save(rev);
        return ResponseEntity.ok("Review created");
            
    }
    
}