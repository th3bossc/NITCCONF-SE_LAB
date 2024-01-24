package com.nitconfbackend.nitconf.models;

import java.util.Date;

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
@Document(collection="sessions")
public class Session {
    @Id public String id;
    public String title;
    public String description;
    public String language;
    public Level level;
    public Status status;
    public User user;
    public Date date;
    @DBRef
    public Tags[] tags;
}
