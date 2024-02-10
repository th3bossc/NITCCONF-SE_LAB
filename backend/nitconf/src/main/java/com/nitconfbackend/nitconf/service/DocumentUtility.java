package com.nitconfbackend.nitconf.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nitconfbackend.nitconf.models.DocumentVersion;

/**
 * DocumentUtility
 * service to handle pdf to byte conversion and vice versa
 * 
 * @since 1.0
 * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
 * @author <a href="https://github.com/Sreeshu123"> Sreeshma Sangesh </a>
 */
@Service
public class DocumentUtility {

    /**
     * pdfToByte
     * converts a pdf file to a stream of bytes
     * 
     * @param file
     * @return - an array of bytes
     * @throws IllegalStateException
     * @throws IOException
     * @see {@link MultipartFile}
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    public byte[] pdfToByte(MultipartFile file) throws IllegalStateException, IOException {
        return file.getBytes();
    }

    /**
     * downloadFile
     * given a list of all documents, finds the latest latest doc, converts the file
     * in the form of bytes to a {@link ByteArrayResource} and returns it
     * 
     * @param allDocs - List of {@link DocumentVersion}s
     * @return {@link ByteArrayResource}
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    public ByteArrayResource downloadFile(List<DocumentVersion> allDocs) {
        allDocs.sort((a, b) -> b.getVersion().compareTo(a.getVersion()));
        int len = allDocs.size();
        if (len > 0) {
            DocumentVersion latestDoc = allDocs.get(len - 1);
            ByteArrayResource resource = new ByteArrayResource(latestDoc.getFile());
            return resource;

        } else
            return null;
    }

}
