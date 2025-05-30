package com.milletproject.millet.website.upload;

import com.cloudinary.Cloudinary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            // Get absolute path of current working directory
            String currentDir = new File(".").getCanonicalPath();
            String uploadDir = currentDir + File.separator + "uploads" + File.separator;

            // Create 'uploads' folder if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("Uploads directory created at: " + uploadDir);
            }

            // Create path for saving file
            Path filePath = Paths.get(uploadDir + file.getOriginalFilename());

            // Save the file
            Files.write(filePath, file.getBytes());
            System.out.println("File saved to: " + filePath);

            // Verify if file exists
            File savedFile = new File(filePath.toString());
            if (savedFile.exists()) {
                System.out.println("✅ YES! File exists after saving.");
            } else {
                System.out.println("❌ NO! File does not exist after saving.");
            }

            return ResponseEntity.ok("✅ File uploaded successfully to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Failed to upload: " + e.getMessage());
        }
    }
}
