package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.model.Cell;
import com.example.pixpalapp.service.StorageService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Override
    public String encodeImage(byte[] imageData) {
        return imageData != null ?
                Base64.getEncoder().encodeToString(imageData) : null;
    }

    public byte[] convertCellsToImage(List<Cell> cells) {
        int width = cells.stream().mapToInt(Cell::getX).max().orElse(0) + 1;
        int height = cells.stream().mapToInt(Cell::getY).max().orElse(0) + 1;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (Cell cell : cells) {
            int rgb = hexToRgbInt(cell.getColor());
            image.setRGB(cell.getX(), cell.getY(), rgb);
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpeg", baos);

            return baos.toByteArray();

        } catch (IOException e) {
            System.err.println("Ошибка при создании MultipartFile: " + e.getMessage());
        }

        return null;
    }

    private int hexToRgbInt(String hexColor) {
        // Remove the hash at the beginning if it exists
        if (hexColor.charAt(0) == '#') {
            hexColor = hexColor.substring(1);
        }

        // If shorthand notation, expand it to 6 characters
        if (hexColor.length() == 3) {
            hexColor = "" + hexColor.charAt(0) + hexColor.charAt(0)
                    + hexColor.charAt(1) + hexColor.charAt(1)
                    + hexColor.charAt(2) + hexColor.charAt(2);
        }

        // Parse the hex string and convert to integer values
        int r = Integer.parseInt(hexColor.substring(0, 2), 16);
        int g = Integer.parseInt(hexColor.substring(2, 4), 16);
        int b = Integer.parseInt(hexColor.substring(4, 6), 16);

        // Combine RGB components into a single int
        return (r << 16) | (g << 8) | b;
    }
}
