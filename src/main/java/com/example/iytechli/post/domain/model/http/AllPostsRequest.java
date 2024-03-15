package com.example.iytechli.post.domain.model.http;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@AllArgsConstructor
@Builder
public class AllPostsRequest {

    // Default values
    private int pageNo;
    private int pageSize;
    private String userId;

}
