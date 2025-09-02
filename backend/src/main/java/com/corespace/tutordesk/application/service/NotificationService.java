package com.corespace.tutordesk.application.service;

import com.corespace.tutordesk.application.dto.StudentDto;

import java.util.List;

public interface NotificationService {
    
    void sendSubscriptionExpiryNotification(List<StudentDto> students);
    
    void sendPaymentReminderNotification(List<StudentDto> students);
    
    void sendWelcomeNotification(StudentDto student);
}
