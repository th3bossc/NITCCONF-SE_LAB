package com.nitconfbackend.nitconf.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nitconfbackend.nitconf.auth.Sessionrequest;
import com.nitconfbackend.nitconf.models.Session;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/session")


public class SessionController {
    public UserRepository userRepo;

    @PostMapping("/newSession")
    public ResponseEntity<Session> newSession(@RequestBody Sessionrequest entity){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        //User user = userRepo.findByEmail(email).orElseThrow();
        Session session = new Session();
        session.setTitle(entity.title);
        session.setLanguage(entity.language);
        session.setLevel(entity.level);
        session.setStatus(entity.status);
        session.setDate(entity.date);
        session.setTags(entity.tags);
        session.setDescription(entity.description);
        return ResponseEntity.ok(session);
    }
}
