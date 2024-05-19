package com.example.pixpalapp.payload.Response.Drawing;

import com.example.pixpalapp.entity.Drawing;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetDrawingsResponse {
    List<Drawing> drawings;
}
