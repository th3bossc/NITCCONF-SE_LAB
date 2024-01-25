package com.nitconfbackend.nitconf.models;

import java.util.Date;
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
    @JsonIgnore public User user;
    public Date date;


    public Session(String title, String description, String language, Level level, Status status, List<Tags> tags) {
        this.title = title;
        this.description = description;
        this.language = language;
        this.level = level;
        this.status = status;
        this.date = new Date();
        this.tags = tags;
    }

    @DBRef
    public List<Tags> tags;
}
