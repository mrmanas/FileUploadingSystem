
package com.manas.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.manas.MetaData.UploadedFile;
import com.manas.Repository.FileRepository;

import java.io.IOException;

@Service
public class FileUploadService {

    @Autowired
    private FileRepository fileRepository;

    public UploadedFile uploadFile(MultipartFile file) throws IOException {
    	String filename = file.getOriginalFilename();
        String fileType = file.getContentType();
        byte[] content = file.getBytes(); // Get the file content as byte array

        UploadedFile uploadedFile = new UploadedFile(filename, fileType, content);
        // Save file metadata and content to MongoDB
        return fileRepository.save(uploadedFile);
    }
}
