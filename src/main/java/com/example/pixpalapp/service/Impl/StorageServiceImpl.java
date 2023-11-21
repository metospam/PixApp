package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String store(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        // Define the path where you want to store the file
        Path destinationPath = Path.of(uploadPath + fileName);

        // Copy the file to the destination path
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
