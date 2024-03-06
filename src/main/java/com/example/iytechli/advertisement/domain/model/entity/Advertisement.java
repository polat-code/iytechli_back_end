package com.example.iytechli.advertisement.domain.model.entity;

import com.example.iytechli.post.domain.model.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(value = "advertisement")
@Data
@AllArgsConstructor
@Builder
public class Advertisement {

   @Id
   private String id;
   private int totalMonitor;
   private List<Photo> photos;
   private String details;

   private Date createdAt;
   private boolean isActive;

}
