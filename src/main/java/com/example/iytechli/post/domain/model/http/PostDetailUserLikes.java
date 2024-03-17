package com.example.iytechli.post.domain.model.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDetailUserLikes {
    
    private String userId;
    private String name;
    private String surname;

}
