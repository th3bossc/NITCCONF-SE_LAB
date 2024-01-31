package com.nitconfbackend.nitconf.repositories;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconfbackend.nitconf.models.User;

public interface UserRepository extends MongoRepository<User, String> {
    /**
     * findByEmail
     * finds a given user by email, if exists
     * @param email
     * @return User : {@link User}
     */
    Optional<User> findByEmail(String email);
    
}
