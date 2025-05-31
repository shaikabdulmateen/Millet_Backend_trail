package com.milletproject.millet.website.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/files")
public class FileUploadController {
    private final Cloudinary cloudinary;

    public FileUploadController(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMultipleFiles(@RequestParam("images") MultipartFile[] files) {
        StringBuilder responseMessage = new StringBuilder();

        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // Cloudinary par upload
                    cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

                    // File name print
                    System.out.println("Uploaded: " + file.getOriginalFilename());
                    System.out.println("Files uploaded successfully and stored at cloudinery");
                    responseMessage.append("✅ Uploaded: ").append(file.getOriginalFilename()).append("\n");
                }
            }
            return ResponseEntity.ok(responseMessage.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Upload failed: " + e.getMessage());
        }
    }
}





