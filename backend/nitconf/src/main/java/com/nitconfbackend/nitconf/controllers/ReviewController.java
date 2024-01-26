package com.nitconfbackend.nitconf.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nitconfbackend.nitconf.RequestTypes.ReviewRequest;
import com.nitconfbackend.nitconf.models.DocumentVersion;
import com.nitconfbackend.nitconf.models.Review;
import com.nitconfbackend.nitconf.models.Role;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.DocumentVersionRepository;
import com.nitconfbackend.nitconf.repositories.ReviewRepository;
import com.nitconfbackend.nitconf.repositories.UserRepository;

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
    public ResponseEntity<List<Review>> getMethodName(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        DocumentVersion doc = docRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(revRepo.findByDoc(doc));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Review revi = revRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(revi);
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<String> createReview(@RequestBody ReviewRequest body, @PathVariable String id) {
        Review rev = new Review();
        rev.comment=body.comment;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User profile = userRepo.findByEmail(email).orElseThrow();
        System.out.println(profile);
        if (id == null)
            return ResponseEntity.notFound().build();
        if (profile.role != Role.REVIEWER && profile.role != Role.PROGRAM_COMMITTEE) {
            System.out.println("Helloooooo");
            return ResponseEntity.badRequest().build();
        }
        DocumentVersion docu = docRepo.findById(id).orElseThrow() ;
        rev.doc=docu;
        rev.reviewer=profile;
        revRepo.save(rev);
        return ResponseEntity.ok("Review created");
    }
    
}