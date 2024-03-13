package com.nitconfbackend.nitconf.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.nitconfbackend.nitconf.models.Paper;

public interface PaperRepository extends MongoRepository<Paper, String> {
}
