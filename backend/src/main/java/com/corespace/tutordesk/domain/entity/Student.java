package com.corespace.tutordesk.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "students")
public class Student {
    
    @Id
    private String id;
    
    @Field("name")
    private String name;
    
    @Field("email")
    private String email;
    
    @Field("phone")
    private String phone;
    
    @Field("start_date")
    private LocalDate startDate;
    
    @Field("level")
    private String level;
    
    @Field("status")
    private StudentStatus status;
    
    @Field("monthly_fee")
    private Double monthlyFee;
    
    @Field("payment_day")
    private Integer paymentDay;
    
    @Field("subscription_expiry")
    private LocalDate subscriptionExpiry;
    
    @Field("progress")
    private List<ProgressEntry> progress;
    
    @Field("notes")
    private String notes;
    
    @Field("created_at")
    private LocalDateTime createdAt;
    
    @Field("updated_at")
    private LocalDateTime updatedAt;
    
    public enum StudentStatus {
        ACTIVE, INACTIVE, SUSPENDED, GRADUATED
    }

    public enum StudentLevel {
        BEGINNER, INTERMEDIARY, ADVANCED
    }
}
