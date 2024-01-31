package com.nitconfbackend.nitconf.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconfbackend.nitconf.models.Tag;
import java.util.Optional;


public interface TagsRepository extends MongoRepository<Tag, String> {
    /**
     * findByTitle
     * finds a given tag by title, if exists
     * @param title
     * @return Tag : {@link Tag}
     */
    Optional<Tag> findByTitle(String title);
}
