package com.nitconfbackend.nitconf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitconfbackend.nitconf.models.Role;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.types.ProfileRequest;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    public UserRepository userRepo;

    /**
     * profileDetails
     * returns the profile details of the currently logged in user
     * 
     * @return User : {@link User}
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @GetMapping("")
    public ResponseEntity<User> profileDetails() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(user);
    }

    /**
     * updateProfile
     * updates the profile details of the currently logged in user
     * 
     * @param entity : {@link ProfileRequest}
     * @return success message
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @PutMapping("")
    public ResponseEntity<Object> updateProfile(@RequestBody ProfileRequest entity) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow();

        if (entity.firstName != null)
            user.setFirstName(entity.firstName);
        if (entity.lastName != null)
            user.setLastName(entity.lastName);
        if (entity.phoneNumber != null)
            user.setPhoneNumber(entity.phoneNumber);
        userRepo.save(user);
        return ResponseEntity.ok("Updated successfully");
    }

    /**
     * getUser
     * if request is done by REVIEWER or PROGRAM_COMMITTE ({@link Role}) returns the
     * profile details of the user with the given id
     * else returns error response
     * 
     * @param id
     * @return user : {@link User}
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
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