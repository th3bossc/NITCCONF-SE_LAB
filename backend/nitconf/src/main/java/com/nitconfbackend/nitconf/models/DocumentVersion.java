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
@Document(collection="document")
public class DocumentVersion {
    @Id public String _id;
    public String changesDesc;
    public Byte[] file;
    public Integer version;
    public String date;

    @DBRef
    public User user;
}
