package com.nitconfbackend.nitconf.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nitconfbackend.nitconf.models.DocumentVersion;
import com.nitconfbackend.nitconf.models.Session;
import com.nitconfbackend.nitconf.models.Tag;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.models.Status;

import com.nitconfbackend.nitconf.repositories.DocumentVersionRepository;
import com.nitconfbackend.nitconf.repositories.SessionRepository;
import com.nitconfbackend.nitconf.repositories.TagsRepository;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.service.DocumentUtility;
import com.nitconfbackend.nitconf.types.SessionRequest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/session")
@SecurityRequirement(name = "Bearer Authentication")
public class SessionController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private DocumentVersionRepository docRepo;

    @Autowired
    private DocumentUtility documentUtility;

    @Autowired
    private TagsRepository tagsRepo;

    /**
     * getAllSessions
     * Returns a list of all sessions of the authenticated user
     * 
     * @return Sessions List of {@link Session}s
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @GetMapping("")
    public ResponseEntity<List<Session>> getAllSessions() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findByEmail(email).orElseThrow();

        List<Session> sessions = currentUser.getSessions();
        return ResponseEntity.ok(sessions);
    }

    /**
     * newSession
     * Creates a new session into the database with the given details
     * 
     * @param entity {@link SessionRequest}
     * @return session {@link session}
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @PostMapping("")
    public ResponseEntity<Session> newSession(@RequestBody SessionRequest entity) {
        if (entity.title == null || entity.language == null || entity.description == null || entity.level == null
                || entity.status == null || entity.tags == null)
            return ResponseEntity.badRequest().build();

        List<Tag> tags = new ArrayList<Tag>();
        entity.tags.forEach(tag -> {
            if (tag != null) {
                Tag newTag = tagsRepo.findById(tag).orElseThrow();
                tags.add(newTag);
            }
        });

        Session session = new Session(
                entity.title,
                entity.description,
                entity.language,
                entity.level,
                entity.status,
                tags);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findByEmail(email).orElseThrow();
        sessionRepo.save(session);

        currentUser.getSessions().add(session);
        userRepo.save(currentUser);

        tags.forEach(tag -> {
            tag.getSessions().add(session);
            tagsRepo.save(tag);
        });
        return ResponseEntity.ok(session);
    }

    /**
     * updateSession
     * Finds the session corresponding to the inputted id, and updates the details
     * with the details provided by the client
     * In case of any error during searching of the database or updation, an
     * appropriate error messagage is thrown
     * 
     * @param id     - id of the session to be updated
     * @param entity - {@link SessionRequest}
     * @return {@link Session}
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable String id, @RequestBody SessionRequest entity) {
        if (id == null)
            return ResponseEntity.notFound().build();
        if (entity.title == null || entity.language == null || entity.description == null || entity.level == null
                || entity.status == null || entity.tags == null)
            return ResponseEntity.badRequest().build();
        Session session = sessionRepo.findById(id).orElseThrow();

        List<Tag> tags = new ArrayList<Tag>();
        entity.tags.forEach(tag -> {
            if (tag != null) {
                Tag newTag = tagsRepo.findById(tag).orElseThrow();
                tags.add(newTag);
            }
        });

        session.setTitle(entity.title);
        session.setDescription(entity.description);
        session.setLanguage(entity.language);
        session.setLevel(entity.level);
        session.setStatus(entity.status);
        session.setTags(tags);

        sessionRepo.save(session);

        return ResponseEntity.ok(session);
    }

    /**
     * updloadPdf
     * Finds the session with the inputted id, and creates a new instance of
     * DocumentVersion and links the uploaded .pdf file with the session
     * In case of any error during searching of the database or updation, an
     * appropriate error messagage is thrown
     * 
     * @param id   - id of the session to be updated
     * @param file - uploaded pdf file
     * @return success message
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @PutMapping("/doc/{id}")
    public ResponseEntity<?> uploadPdf(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Session session = sessionRepo.findById(id).orElseThrow();
        try {
            byte[] data = documentUtility.pdfToByte(file);
            List<DocumentVersion> allDocs = session.getDocumentVersions();
            if (data == null)
                return ResponseEntity.notFound().build();
            DocumentVersion newDoc = new DocumentVersion(
                    "New Submission",
                    data,
                    allDocs.size() + 1
            // session
            );
            docRepo.save(newDoc);
            session.getDocumentVersions().add(newDoc);
            sessionRepo.save(session);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    /**
     * getDocument
     * Finds the session with the inputted id, and sends the pdf file to the user
     * 
     * @param id - id of the session
     * @return {@link Resource}
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @GetMapping("/doc/{id}")
    public ResponseEntity<Resource> getDocument(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Session session = sessionRepo.findById(id).orElseThrow();
        List<DocumentVersion> allDocs = session.getDocumentVersions();
        ByteArrayResource resource = documentUtility.downloadFile(allDocs);
        if (resource == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= " + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    /**
     * getSession
     * returns the session with the inputted id.
     * In case of an invalid id, a 404 error is thrown
     * 
     * @return {@link Session}
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Session> getSession(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Session session = sessionRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(session);
    }

    /**
     * updateStatusToAccepted
     * Updates the status of the session with the inputted id to
     * <strong>ACCEPTED</strong>
     * 
     * @param id - id of the session to be updated
     * @return "UPDATED STATUS TO ACCEPTED"
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @PutMapping("/status/accepted/{id}")
    public ResponseEntity<String> updateStatusToAccepted(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Session session = sessionRepo.findById(id).orElseThrow();
        session.setStatus(Status.ACCEPTED);
        return ResponseEntity.ok("UPDATED STATUS TO ACCEPTED");
    }

    /**
     * updateStatusToAccepted
     * Updates the status of the session with the inputted id to
     * <strong>REJECTED</strong>
     * 
     * @param id - id of the session to be updated
     * @return "UPDATED STATUS TO ACCEPTED"
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @PutMapping("/status/rejected/{id}")
    public ResponseEntity<String> updateStatusToRejected(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Session session = sessionRepo.findById(id).orElseThrow();
        session.setStatus(Status.REJECTED);
        return ResponseEntity.ok("UPDATED STATUS TO REJECTED");
    }

    /**
     * deleteSession
     * Finds the session with the inputted id, and deletes it, if it exists
     * 
     * @param id - id of the session to be deleted
     * @return "DELETED SESSION"
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Session session = sessionRepo.findById(id).orElseThrow();
        if (session != null)
            sessionRepo.delete(session);
        return ResponseEntity.ok("DELETED SESSION");
    }

}