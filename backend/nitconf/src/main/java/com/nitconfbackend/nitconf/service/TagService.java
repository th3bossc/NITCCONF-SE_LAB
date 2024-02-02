package com.nitconfbackend.nitconf.service;

import com.nitconfbackend.nitconf.models.Session;
import com.nitconfbackend.nitconf.models.Tag;
import com.nitconfbackend.nitconf.repositories.TagsRepository;
import com.nitconfbackend.nitconf.types.TagRequest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TagService {

    private final TagsRepository tagsRepository;

    public TagService(TagsRepository repo)
    {
        this.tagsRepository=repo;
    }

    public List<Tag> getAllTags()
    {   
        return tagsRepository.findAll();
    }

    public Tag CreateNewTag(string title)
    {
        Tag newtag= new Tag(title);
        tagsRepository.save(newtag);
        return newtag;
    }
    public List<Session> findSessions(string title){
        Tag tag =  tagsRepository.findByTitle(title).orElseThrow();
        List<Session> relatedSessions = tag.getSessions();
        return relatedSessions;
    }
}


