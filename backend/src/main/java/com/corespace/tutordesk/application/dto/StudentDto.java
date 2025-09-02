package com.corespace.tutordesk.application.dto;

import com.corespace.tutordesk.domain.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    
    private String id;
    private String name;
    private String email;
    private String phone;
    private LocalDate startDate;
    private String level;
    private Student.StudentStatus status;
    private Double monthlyFee;
    private Integer paymentDay;
    private LocalDate subscriptionExpiry;
    private List<ProgressEntryDto> progress;
    private String notes;
}
