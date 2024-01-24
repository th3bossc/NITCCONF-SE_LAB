package com.nitconfbackend.nitconf.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconfbackend.nitconf.models.DocumentVersion;
import java.util.Date;
import com.nitconfbackend.nitconf.models.Session;


public interface DocumentVersionRepository extends MongoRepository<DocumentVersion, String>  {
    List<DocumentVersion> findBySessionOrderByDate(Session session, Date date);
}
