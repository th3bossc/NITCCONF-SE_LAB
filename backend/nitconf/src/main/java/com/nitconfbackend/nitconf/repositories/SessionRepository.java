package com.nitconfbackend.nitconf.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.nitconfbackend.nitconf.models.Session;


public interface SessionRepository extends MongoRepository<Session, String> {
}
