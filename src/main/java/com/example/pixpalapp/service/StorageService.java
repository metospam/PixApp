package com.example.pixpalapp.service;

import com.example.pixpalapp.model.Cell;

import java.util.List;

public interface StorageService {
    String encodeImage(byte[] imageData);
    byte[] convertCellsToImage(List<Cell> cells);
}
