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
import com.nitconfbackend.nitconf.service.TagService;
import com.nitconfbackend.nitconf.types.TagRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/tags")
public class Tagcontroller {

    @Autowired
    private TagService tagService;

    @GetMapping("/{title}")
    public ResponseEntity<List<Session>> FindSessions(@PathVariable String title) {
        return ResponseEntity.ok(tagService.findSessions(title));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Tag>> FindAll() {

        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PostMapping("/newtag")
    public ResponseEntity<Tag> newtag(@RequestBody TagRequest entity) {
        return ResponseEntity.ok(tagService.CreateNewTag(entity.title));
    }

}
