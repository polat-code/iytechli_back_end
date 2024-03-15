package com.example.iytechli.post.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class Photo {

    // Base64 data type
    private String image;
    private Date createdAt;
}
