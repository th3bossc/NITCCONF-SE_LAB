package com.nitconfbackend.nitconf.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="tags")
public class Tag {
    @Id public String id;
    public String title;

    @DBRef
    @JsonIgnore
    public List<Session> sessions;


    public Tag(String title) {
        this.title = title;
        this.sessions= new ArrayList<Session>();
    }
}


