package com.nitconfbackend.nitconf.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconfbackend.nitconf.models.DocumentVersion;
import java.util.List;


public interface DocumentVersionRepository extends MongoRepository<DocumentVersion, String>  {
    /**
     * findByVersion
     * finds a given document version by version, if exists
     * @param version
     * @return DocumentVersion : {@link DocumentVersion}
     */
    List<DocumentVersion> findByVersion(Integer version);
}
