package com.nitconfbackend.nitconf.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nitconfbackend.nitconf.models.Session;
import com.nitconfbackend.nitconf.models.Tag;
import com.nitconfbackend.nitconf.repositories.TagsRepository;
import com.nitconfbackend.nitconf.types.TagRequest;

public class TagcontrollerTest {

    @Mock
    private TagsRepository tagsRepository;

    @InjectMocks
    private Tagcontroller tagController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindSessions() {
        // Prepare test data
        String title = "TestTag";
        Tag tag = new Tag();
        tag.setTitle(title);
        List<Session> sessions = new ArrayList<>();
        Session session1 = new Session();
        Session session2 = new Session();
        sessions.add(session1);
        sessions.add(session2);
        tag.setSessions(sessions);

        // Mock repository behavior
        when(tagsRepository.findByTitle(title)).thenReturn(Optional.of(tag));

        // Call the controller method
        ResponseEntity<List<Session>> responseEntity = tagController.FindSessions(title);

        // Verify the response
        assertEquals(sessions, responseEntity.getBody());
    }

    @Test
    public void testFindAll() {
        // Prepare test data
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tags.add(tag1);
        tags.add(tag2);

        // Mock repository behavior
        when(tagsRepository.findAll()).thenReturn(tags);

        // Call the controller method
        ResponseEntity<List<Tag>> responseEntity = tagController.FindAll();

        // Verify the response
        assertEquals(tags, responseEntity.getBody());
    }

    @Test
    public void testNewTag() {
        // Prepare test data
        TagRequest tagRequest = new TagRequest();
        tagRequest.setTitle("TestTag");
        Tag newTag = new Tag(tagRequest.getTitle());

        // Mock repository behavior
        when(tagsRepository.save(any(Tag.class))).thenReturn(newTag);

        // Call the controller method
        ResponseEntity<Tag> responseEntity = tagController.newtag(tagRequest);

        // Verify the response
        assertEquals(newTag, responseEntity.getBody());
    }
}
