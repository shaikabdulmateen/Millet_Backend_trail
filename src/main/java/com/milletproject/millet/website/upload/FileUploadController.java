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
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
        try {
            // File ko Cloudinary par upload karo
            cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            // User ko sirf success message bhejo
            System.out.println("File name :-" + file.getOriginalFilename() );
            System.out.println("✅ Image successfully stored and Exist in cloudinary .");
            return ResponseEntity.ok("✅ Image uploaded successfully!" + file.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Upload failed: " + e.getMessage());
        }
    }

}



