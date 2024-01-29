package com.nitconfbackend.nitconf.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nitconfbackend.nitconf.models.DocumentVersion;

@Service
public class DocumentUtility {
    public byte[] pdfToByte(MultipartFile file) throws IllegalStateException, IOException {
        return file.getBytes();
    }
    public ByteArrayResource downloadFile(List<DocumentVersion> allDocs) {
        allDocs.sort((a, b) -> b.getVersion().compareTo(a.getVersion()));
        int len = allDocs.size();
        if (len > 0) {
            DocumentVersion latestDoc = allDocs.get(len - 1);
            ByteArrayResource resource = new ByteArrayResource(latestDoc.getFile());
            return resource;

        }
        else 
            return null;
    }

}
