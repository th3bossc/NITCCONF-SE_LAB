package com.nitconfbackend.nitconf.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

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

    public DocumentVersion(String changesDesc, @NonNull byte[] file, @NonNull Integer version, Session session) {
        this.changesDesc = changesDesc;
        this.file = file;
        this.version = version;
        this.date = new Date();
        this.session = session;
    }


    @Id public String id;

    @JsonProperty public String changesDesc;
    @JsonProperty @NonNull public byte[] file;
    @JsonProperty @NonNull public Integer version;
    @JsonIgnore @NonNull public Date date;


    @DBRef
    public Session session;
}
