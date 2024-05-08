package com.example.pixpalapp.payload.Response;

import com.example.pixpalapp.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
public class UserResponse {

    String imageBase64;
    User user;

}
