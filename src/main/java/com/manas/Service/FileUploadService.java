
package com.manas.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.manas.MetaData.UploadedFile;
import com.manas.Repository.FileRepository;

import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Sort;


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
    public List<UploadedFile> getAllFiles() {
        return fileRepository.findAll();
    }

    public void deleteFile(String fileId) {
        fileRepository.deleteById(fileId);
    }
    public UploadedFile getFileById(String fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found with id " + fileId));
    }
    public List<UploadedFile> searchAndSortFiles(String search, String sort) {
        Sort sorting = Sort.unsorted();
        if ("dateAsc".equals(sort)) {
            sorting = Sort.by(Sort.Direction.ASC, "uploadDate");
        } else if ("dateDesc".equals(sort)) {
            sorting = Sort.by(Sort.Direction.DESC, "uploadDate");
        } else if ("sizeAsc".equals(sort)) {
            sorting = Sort.by(Sort.Direction.ASC, "size");
        } else if ("sizeDesc".equals(sort)) {
            sorting = Sort.by(Sort.Direction.DESC, "size");
        }

        if (search != null && !search.isEmpty()) {
            return fileRepository.findByFilenameContainingIgnoreCase(search, sorting);
        } else {
            return fileRepository.findAll(sorting);
        }
    }
}
