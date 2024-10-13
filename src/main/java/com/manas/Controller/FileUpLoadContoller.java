package com.manas.Controller;

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
    private FileUploadService fileUploadService; // Inject your file upload service

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload"; // Return the name of the upload HTML template
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "upload"; // Return to the upload form if no file is selected
        }

        try {
            // Call the service to handle the file upload
            UploadedFile uploadedFile = fileUploadService.uploadFile(file);
            model.addAttribute("message", "File uploaded successfully: " + uploadedFile.getFilename());
        } catch (Exception e) {
            model.addAttribute("message", "File upload failed: " + e.getMessage());
        }

        return "upload"; // Return to the upload form with the message
    }
}
