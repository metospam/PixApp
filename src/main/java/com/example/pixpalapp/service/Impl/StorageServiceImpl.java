package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String store(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            Path destinationPath = Path.of(uploadPath + fileName);

            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String encodeImage(byte[] imageData) {
        return imageData != null ?
                Base64.getEncoder().encodeToString(imageData) : null;
    }
}
