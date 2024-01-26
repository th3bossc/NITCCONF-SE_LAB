package com.nitconfbackend.nitconf.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconfbackend.nitconf.models.Session;
import com.nitconfbackend.nitconf.models.User;

import java.util.List;


public interface SessionRepository extends MongoRepository<Session, String> {
    List<Session> findByUser(User user);
}
