package com.example.iytechli.post.domain.model.http;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllPostsRequest {

    // Default values
    private int pageNo = 0;
    private int pageSize = 15;
    @NonNull
    private String userId;

}
