package com.nitconfbackend.nitconf.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconfbackend.nitconf.models.Tag;
import java.util.Optional;


public interface TagsRepository extends MongoRepository<Tag, String> {
    Optional<Tag> findByTitle(String title);
}
