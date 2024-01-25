package com.nitconfbackend.nitconf.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty public String changesDesc;
    @JsonProperty public Byte[] file;
    @JsonProperty public Integer version;
    @JsonIgnore public Date date;

    @DBRef
    public User user;


    @DBRef
    public Session session;
}
