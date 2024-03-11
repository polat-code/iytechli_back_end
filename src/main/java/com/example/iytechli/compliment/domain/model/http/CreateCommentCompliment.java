package com.example.iytechli.compliment.domain.model.http;

import com.example.iytechli.compliment.domain.model.entity.ReportReason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentCompliment {

    private String commentId;
    private String userId;
    private ReportReason reportReason;
    private String description;
}
