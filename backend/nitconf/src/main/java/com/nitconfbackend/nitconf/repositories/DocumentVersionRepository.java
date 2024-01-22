package com.nitconfbackend.nitconf.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconfbackend.nitconf.models.DocumentVersion;

public interface DocumentVersionRepository extends MongoRepository<DocumentVersion, String>  {
    
}
