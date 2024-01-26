package com.nitconfbackend.nitconf.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconfbackend.nitconf.models.Tags;
import java.util.Optional;


public interface TagsRepository extends MongoRepository<Tags, String> {
    Optional<Tags> findByTitle(String title);
}
