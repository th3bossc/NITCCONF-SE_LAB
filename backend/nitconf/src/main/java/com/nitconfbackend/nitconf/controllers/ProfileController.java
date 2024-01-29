package com.nitconfbackend.nitconf.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitconfbackend.nitconf.RequestTypes.ProfileRequest;
import com.nitconfbackend.nitconf.models.Role;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;







@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    public UserRepository userRepo;


    //currently logged in user can get his profile details
    @GetMapping("")
    public ResponseEntity<User> profileDetails() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(user);   
    }


    //currently logged in user can update his profile details
    @PutMapping("")
    public ResponseEntity<Object> updateProfile(@RequestBody ProfileRequest entity) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow();
        
        if (user != null) {
            // userRepo.delete(user);
            if (entity.firstName != null)
                user.setFirstName(entity.firstName);
            if (entity.lastName != null)
                user.setLastName(entity.lastName);
            if (entity.phoneNumber != null)
                user.setPhoneNumber(entity.phoneNumber);
            userRepo.save(user);
            return ResponseEntity.ok("Updated successfully");
        }
        return ResponseEntity.badRequest().build();
    }


    //any reviewer or program committee can get profile details of any user
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable @NonNull String id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Role currentUserRole = userRepo.findByEmail(email).orElseThrow().getRole();
        if (currentUserRole != Role.REVIEWER && currentUserRole != Role.PROGRAM_COMMITTEE)
            return ResponseEntity.badRequest().build();
        User user = userRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(user);
    }
    
}