package com.corespace.tutordesk.application.dto;

import com.corespace.tutordesk.domain.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequest {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    private String phone;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private String level;
    
    @NotNull(message = "Monthly fee is required")
    @Positive(message = "Monthly fee must be positive")
    private Double monthlyFee;
    
    @NotNull(message = "Payment day is required")
    private Integer paymentDay;
    
    private String notes;
}
