package com.manas.Controller;

import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.manas.MetaData.UploadedFile;
import com.manas.Service.FileUploadService;

@Controller
public class FileUpLoadContoller {

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "upload";
        }

        try {
            UploadedFile uploadedFile = fileUploadService.uploadFile(file);
            model.addAttribute("message", "File uploaded successfully: " + uploadedFile.getFilename());
        } catch (Exception e) {
            model.addAttribute("message", "File upload failed: " + e.getMessage());
        }

        return "upload";
    }
    
    @GetMapping("/files")
    public String listFiles(@RequestParam(required = false) String search,
                            @RequestParam(required = false) String sort,
                            Model model) {
        List<UploadedFile> files = (search == null && sort == null) 
                ? fileUploadService.getAllFiles()
                : fileUploadService.searchAndSortFiles(search, sort);
                
        model.addAttribute("files", files);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        return "file-list";
    }

    @PostMapping("/delete-file")
    public String deleteFile(@RequestParam("fileId") String fileId, Model model) {
        try {
            fileUploadService.deleteFile(fileId);
            model.addAttribute("message", "File deleted successfully.");
        } catch (Exception e) {
            model.addAttribute("message", "Error deleting file: " + e.getMessage());
        }
        return "redirect:/files";
    }
    
    @GetMapping("/download-file")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("fileId") String fileId) {
        UploadedFile file = fileUploadService.getFileById(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getContent()));
    }
}
