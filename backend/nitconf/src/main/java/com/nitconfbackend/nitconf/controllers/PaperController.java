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
import com.nitconfbackend.nitconf.models.Paper;
import com.nitconfbackend.nitconf.models.Tag;
import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.models.Status;

import com.nitconfbackend.nitconf.repositories.DocumentVersionRepository;
import com.nitconfbackend.nitconf.repositories.PaperRepository;
import com.nitconfbackend.nitconf.repositories.TagsRepository;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.service.DocumentUtility;
import com.nitconfbackend.nitconf.types.PaperRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/paper")

public class PaperController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private DocumentVersionRepository docRepo;

    @Autowired
    private DocumentUtility documentUtility;

    @Autowired
    private TagsRepository tagsRepo;


    /**
     * getAllPapers
     * returns a list of all the papers of logged in user
     * 
     * @return List of {@Link Paper}s
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @GetMapping("")
    public ResponseEntity<List<Paper>> getAllPapers() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findByEmail(email).orElseThrow();

        List<Paper> papers = currentUser.getPapers();
        return ResponseEntity.ok(papers);
    }

    /**
     * newPaper
     * Creates and returns a new paper with inputted title and tag list
     * 
     * @param entity {@link PaperRequest}
     * @return {@Link Paper}
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @PostMapping("")
    public ResponseEntity<Paper> newPaper(@RequestBody PaperRequest entity) {
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

        Paper paper = new Paper(
                entity.title,
                entity.description,
                entity.language,
                entity.level,
                entity.status,
                tags);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findByEmail(email).orElseThrow();

        paperRepository.save(paper);

        currentUser.getPapers().add(paper);
        userRepo.save(currentUser);

        tags.forEach(tag -> {
            tag.getPapers().add(paper);
            tagsRepo.save(tag);
        });
        return ResponseEntity.ok(paper);
    }

    /**
     * updatePaper
     * Updates paper details with inputted information
     * 
     * @param entity {@link PaperRequest}
     * @return {@Link Paper}
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @PutMapping("/{id}")
    public ResponseEntity<Paper> updatePaper(@PathVariable String id, @RequestBody PaperRequest entity) {
        if (id == null)
            return ResponseEntity.notFound().build();
        if (entity.title == null || entity.language == null || entity.description == null || entity.level == null
                || entity.status == null || entity.tags == null)
            return ResponseEntity.badRequest().build();
        Paper paper = paperRepository.findById(id).orElseThrow();

        List<Tag> tags = new ArrayList<Tag>();
        entity.tags.forEach(tag -> {
            if (tag != null) {
                Tag newTag = tagsRepo.findById(tag).orElseThrow();
                tags.add(newTag);
            }
        });

        paper.setTitle(entity.title);
        paper.setDescription(entity.description);
        paper.setLanguage(entity.language);
        paper.setLevel(entity.level);
        paper.setStatus(entity.status);
        paper.setTags(tags);

        paperRepository.save(paper);

        return ResponseEntity.ok(paper);
    }

    /**
     * uploadPdf
     *  Uploads the PDF file to the paper with given id
     * 
     * @return Response: Success message
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @PutMapping("/doc/{id}")
    public ResponseEntity<?> uploadPdf(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Paper paper = paperRepository.findById(id).orElseThrow();
        try {
            byte[] data = documentUtility.pdfToByte(file);
            List<DocumentVersion> allDocs = paper.getDocumentVersions();
            if (data == null)
                return ResponseEntity.notFound().build();
            DocumentVersion newDoc = new DocumentVersion(
                    "New Submission",
                    data,
                    allDocs.size() + 1
            );
            docRepo.save(newDoc);
            paper.getDocumentVersions().add(newDoc);
            paperRepository.save(paper);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    /**
     * getDocument
     * Returns the latest document of the given paper id
     * 
     * @return {@Link Paper}
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @GetMapping("/doc/{id}")
    public ResponseEntity<Resource> getDocument(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Paper paper = paperRepository.findById(id).orElseThrow();
        List<DocumentVersion> allDocs = paper.getDocumentVersions();
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
     * getPaper
     * Returns the paper with given id     
     * @return {@Link Paper}
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Paper> getPaper(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Paper paper = paperRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(paper);
    }

    /**
     * updateStatusToAccepted
     * Sets the status of paper with given id as ACCEPTED    
     * @return Response: Success message
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @PutMapping("/status/accepted/{id}")
    public ResponseEntity<String> updateStatusToAccepted(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Paper paper = paperRepository.findById(id).orElseThrow();
        paper.setStatus(Status.ACCEPTED);
        return ResponseEntity.ok("UPDATED STATUS TO ACCEPTED");
    }

    /**
     * updateStatusToRejected
     * Sets the status of paper with given id as REJECTED    
     * @return Response: Success message
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @PutMapping("/status/rejected/{id}")
    public ResponseEntity<String> updateStatusToRejected(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Paper paper = paperRepository.findById(id).orElseThrow();
        paper.setStatus(Status.REJECTED);
        return ResponseEntity.ok("UPDATED STATUS TO REJECTED");
    }

    /**
     * deletePaper
     * Deletes the paper with given id     
     * @return Response: Success message
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaper(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.notFound().build();
        Paper paper = paperRepository.findById(id).orElseThrow();
        if (paper != null)
            paperRepository.delete(paper);
        return ResponseEntity.ok("DELETED SESSION");
    }

}
