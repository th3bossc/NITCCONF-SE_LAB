package com.nitconfbackend.nitconf.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
// import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="document")
public class DocumentVersion {

    
    
    @Id public String id;
    
    public String changesDesc;
    @NonNull 
    @JsonIgnore
    public byte[] file;
    @NonNull public Integer version;
    @JsonIgnore @NonNull public Date date;
    @DBRef
    public List<Review> reviews;
    
    // @DBRef
    // public Session session;
    
    
    public DocumentVersion(String changesDesc, @NonNull byte[] file, @NonNull Integer version /* ,Session session*/) {
        this.changesDesc = changesDesc;
        this.file = file;
        this.version = version;
        this.date = new Date();
        this.reviews = new ArrayList<Review>();
        // this.session = session;
    }
}
