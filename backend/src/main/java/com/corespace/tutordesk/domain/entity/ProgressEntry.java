package com.corespace.tutordesk.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressEntry {
    
    @Field("date")
    private LocalDate date;
    
    @Field("topic")
    private String topic;
    
    @Field("description")
    private String description;
    
    @Field("grade")
    private Double grade;
    
    @Field("max_grade")
    private Double maxGrade;
    
    @Field("comments")
    private String comments;
    
    @Field("created_at")
    private LocalDateTime createdAt;
}
