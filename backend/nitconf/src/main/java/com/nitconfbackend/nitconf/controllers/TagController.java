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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/tags")
public class TagController {
    @Autowired
    private TagsRepository repository;
    
    @GetMapping("/{title}")
    public ResponseEntity<List<Session>> FindSessions(@PathVariable String title) {
        Tag tag =  repository.findByTitle(title).orElseThrow();
        List<Session> relatedSessions = tag.getSessions();

        return ResponseEntity.ok(relatedSessions);
    }

    @GetMapping("")
    public ResponseEntity<List<Tag>> FindAll() {
        List<Tag> tags = repository.findAll();
        return ResponseEntity.ok(tags);
    }

    @PostMapping("")
    public ResponseEntity<Tag> newtag(@RequestBody TagRequest entity) {
       Tag newtag= new Tag(entity.title);
        repository.save(newtag);
        return ResponseEntity.ok(newtag);
    }
    
}