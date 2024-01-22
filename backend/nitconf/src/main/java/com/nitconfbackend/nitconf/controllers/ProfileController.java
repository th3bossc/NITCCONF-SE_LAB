package com.nitconfbackend.nitconf.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitconfbackend.nitconf.auth.ProfileResponse;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;





@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    public UserRepository userRepo;

    @GetMapping("")
    public ResponseEntity<ProfileResponse> profileDetails() {
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfileResponse profile = new ProfileResponse(principal.getFirstName(), principal.getLastName(), principal.getEmail(), principal.getPhoneNumber(), principal.getRole());
        return ResponseEntity.ok(profile);
        
    }
}