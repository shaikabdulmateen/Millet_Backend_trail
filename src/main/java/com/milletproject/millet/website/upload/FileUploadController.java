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
    public ResponseEntity<String> uploadFiles(@RequestParam("file") MultipartFile[] files) {
        StringBuilder responseMessage = new StringBuilder();

        for (MultipartFile file : files) {
            try {
                // File ko Cloudinary par upload karo
                cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

                // Console logs
                System.out.println("File name :- " + file.getOriginalFilename());
                System.out.println("✅ Image successfully stored and exists in Cloudinary.");

                // Response message append
                responseMessage.append("✅ Image uploaded successfully: ")
                        .append(file.getOriginalFilename())
                        .append("\n");

            } catch (IOException e) {
                e.printStackTrace();
                responseMessage.append("❌ Upload failed for: ")
                        .append(file.getOriginalFilename())
                        .append(" - ")
                        .append(e.getMessage())
                        .append("\n");
            }
        }

        return ResponseEntity.ok(responseMessage.toString());
    }


}



