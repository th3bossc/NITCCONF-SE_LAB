package com.nitconfbackend.nitconf.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitconfbackend.nitconf.models.Session;
import com.nitconfbackend.nitconf.models.Tag;
import com.nitconfbackend.nitconf.repositories.TagsRepository;
import com.nitconfbackend.nitconf.types.TagRequest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/tags")
@SecurityRequirement(name = "Bearer Authentication")
public class TagController {
    @Autowired
    private TagsRepository repository;

    /**
     * findSessions
     * Finds the tag with the given id, and returns all sessions that has that tag
     * 
     * @param id - id of the Tag
     * @return List of {@link Tag}s
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<Session>> FindSessions(@PathVariable String id) {
        if (id == null)
            return ResponseEntity.badRequest().build();
        Tag tag = repository.findById(id).orElseThrow();
        List<Session> relatedSessions = tag.getSessions();

        return ResponseEntity.ok(relatedSessions);
    }

    /**
     * findAll
     * returns a list of all the tags in the database
     * 
     * @return List of {@Link Tag}s
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @GetMapping("")
    public ResponseEntity<List<Tag>> FindAll() {
        List<Tag> tags = repository.findAll();
        return ResponseEntity.ok(tags);
    }

    /**
     * newTAg
     * creates a new tag with inputted title
     * 
     * @param entity {@link TagRequest}
     * @return {@link Tag}
     * @since 1.0
     * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
     */
    @PostMapping("")
    public ResponseEntity<Tag> newtag(@RequestBody TagRequest entity) {
        Tag newtag = new Tag(entity.title);
        repository.save(newtag);
        return ResponseEntity.ok(newtag);
    }

}