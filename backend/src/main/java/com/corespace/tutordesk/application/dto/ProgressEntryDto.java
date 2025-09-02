package com.corespace.tutordesk.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressEntryDto {
    
    private String id;
    private LocalDate date;
    private String topic;
    private String description;
    private Double grade;
    private Double maxGrade;
    private String comments;
}
