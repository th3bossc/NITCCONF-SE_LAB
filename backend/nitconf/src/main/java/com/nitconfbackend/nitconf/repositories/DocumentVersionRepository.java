package com.nitconfbackend.nitconf.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconfbackend.nitconf.models.DocumentVersion;
import java.util.Optional;
import java.util.List;


public interface DocumentVersionRepository extends MongoRepository<DocumentVersion, String>  {
    Optional<DocumentVersion> findById(String id);
    List<DocumentVersion> findByVersion(Integer version);
}
