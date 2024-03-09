package com.example.iytechli.post.domain.model.http;

import com.example.iytechli.post.domain.model.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePostRequest {
    private String content;
    private String contentOwnerUserId;
    private List<CreatePostPhotoRequest> photoList;
    private String noteToAdmin;

}
