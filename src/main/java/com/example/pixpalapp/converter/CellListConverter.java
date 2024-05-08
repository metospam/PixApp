package com.example.pixpalapp.converter;

import com.example.pixpalapp.model.Cell;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class CellListConverter implements AttributeConverter<List<Cell>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Cell> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting List<Cell> to JSON", e);
        }
    }

    @Override
    public List<Cell> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to List<Cell>", e);
        }
    }
}
