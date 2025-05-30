package com.milletproject.millet.website.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/files")
public class FileUploadController {

    private final Cloudinary cloudinary;

    public FileUploadController() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", System.getenv("CLOUDINARY_CLOUD_NAME"),
                "api_key", System.getenv("CLOUDINARY_API_KEY"),
                "api_secret", System.getenv("CLOUDINARY_API_SECRET")
        ));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
        try {
            // Convert MultipartFile to File
            File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile);

            // Upload to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());

            // Clean up temp file
            tempFile.delete();

            // Get URL
            String imageUrl = (String) uploadResult.get("secure_url");

            return ResponseEntity.ok("✅ Uploaded to Cloudinary: " + imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Upload failed: " + e.getMessage());
        }
    }
}
