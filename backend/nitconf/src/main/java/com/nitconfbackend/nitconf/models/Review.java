package com.nitconfbackend.nitconf.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="reviews")
public class Review {
    @Id public String id;
    @DBRef
    public User reviewer;
    public String comment;
    
    // @DBRef
    // public DocumentVersion doc;
    
}
