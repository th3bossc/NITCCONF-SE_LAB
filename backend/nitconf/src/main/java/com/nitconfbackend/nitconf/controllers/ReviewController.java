package com.nitconfbackend.nitconf.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nitconfbackend.nitconf.models.DocumentVersion;
import com.nitconfbackend.nitconf.models.Review;
import com.nitconfbackend.nitconf.repositories.DocumentVersionRepository;
import com.nitconfbackend.nitconf.repositories.ReviewRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController

@RequestMapping("/api")
public class ReviewController{

    @Autowired
    private DocumentVersionRepository docRepo;

    @Autowired
    private ReviewRepository revRepo;

    @GetMapping("/reviews/{id}")
    public ResponseEntity<List<Review>> getMethodName(@PathVariable String id) {
        DocumentVersion doc = docRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(revRepo.findByDoc(doc));
    }

    @GetMapping("/review/{id}")
    public Optional<Review> getReview(@PathVariable String id) {
        return revRepo.findById(id);
    }
    
    @PostMapping("/review/{id}")
    public void createReview(@RequestBody String Comment ,@PathVariable String id) {
        Review rev = new Review();
        rev.comment=Comment;
        DocumentVersion docu = docRepo.findById(id).orElseThrow();
        rev.doc=docu;
        revRepo.save(rev);
    }
    
}