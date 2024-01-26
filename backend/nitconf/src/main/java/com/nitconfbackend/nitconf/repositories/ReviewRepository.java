package com.nitconfbackend.nitconf.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

// import com.nitconfbackend.nitconf.models.DocumentVersion;
import com.nitconfbackend.nitconf.models.Review;

// import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    // List<Review> findByDoc(DocumentVersion doc);

}
