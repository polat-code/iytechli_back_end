package com.example.iytechli.post.domain.model.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllPostsRequest {

    // Default values
    private int pageNo = 0;
    private int pageSize = 15;

}
