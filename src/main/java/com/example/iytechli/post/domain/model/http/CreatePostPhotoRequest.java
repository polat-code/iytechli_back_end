package com.example.iytechli.post.domain.model.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePostPhotoRequest {

    private String name;
    // Base64 data type
    private String image;
}
