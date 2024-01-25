package com.nitconfbackend.nitconf.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconfbackend.nitconf.models.Tags;

public interface TagsRepository extends MongoRepository<Tags, String> {
    
}
