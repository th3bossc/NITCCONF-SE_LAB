package com.nitconfbackend.nitconf.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nitconfbackend.nitconf.auth.SessionRequest;
import com.nitconfbackend.nitconf.auth.SessionResponse;
import com.nitconfbackend.nitconf.models.DocumentVersion;
import com.nitconfbackend.nitconf.models.Session;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.DocumentVersionRepository;
import com.nitconfbackend.nitconf.repositories.SessionRepository;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/session")


public class SessionController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private DocumentVersionRepository docRepo;

    @PostMapping("/new")
    public ResponseEntity<Session> newSession(@RequestBody SessionRequest entity, @RequestParam("file") MultipartFile file){
        Session session = new Session();
        session.setTitle(entity.title);
        session.setLanguage(entity.language);
        session.setLevel(entity.level);
        session.setStatus(entity.status);
        session.setDate(entity.date);
        session.setTags(entity.tags);
        session.setDescription(entity.description);
        sessionRepo.save(session);



        DocumentVersion newDoc = new DocumentVersion();
        newDoc.changesDesc = "First Submission";
        newDoc.date = new Date();
        newDoc.session = session;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow();
        newDoc.user = user;
        newDoc.version = 1;
        try {
            newDoc.file = file.getBytes();
            docRepo.save(newDoc);
            return ResponseEntity.ok(session);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<SessionResponse> getSession(@RequestParam String id) {
        Session session = sessionRepo.findById(id).orElseThrow();
        List<DocumentVersion> allDocs = docRepo.findBySessionOrderByDate(session);
        DocumentVersion latestDoc= allDocs.get(0);
        SessionResponse response = new SessionResponse();
        response.title = session.title;
        response.description = session.description;
        response.language = session.language;
        response.level = session.level;
        response.status = session.status;
        response.date = session.date;
        response.version = latestDoc.version;

        return ResponseEntity.ok(response);
    }
    
}
//New session created to be added to user sessions