package com.corespace.tutordesk.application.service.impl;

import com.corespace.tutordesk.application.dto.StudentDto;
import com.corespace.tutordesk.application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    
    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Override
    public void sendSubscriptionExpiryNotification(List<StudentDto> students) {
        if (students.isEmpty()) {
            log.info("No students with expiring subscriptions to notify");
            return;
        }
        
        log.info("Sending subscription expiry notifications to {} students", students.size());
        
        for (StudentDto student : students) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(student.getEmail());
                message.setSubject("Subscription Expiry Notice - Tutor Desk");
                
                String body = String.format(
                    "Dear %s,\n\n" +
                    "Monthly Fee: $%.2f\n" +
                    "Payment Day: %d\n\n" +
                    "If you have any questions, please contact your teacher.\n\n" +
                    "Best regards,\nTutor Desk Team",
                    student.getName(),
                    student.getMonthlyFee(),
                    student.getPaymentDay()
                );
                
                message.setText(body);
                mailSender.send(message);
                
                log.info("Subscription expiry notification sent to: {}", student.getEmail());
            } catch (Exception e) {
                log.error("Failed to send subscription expiry notification to: {}", student.getEmail(), e);
            }
        }
    }
    
    @Override
    public void sendPaymentReminderNotification(List<StudentDto> students) {
        if (students.isEmpty()) {
            log.info("No students to send payment reminders to");
            return;
        }
        
        log.info("Sending payment reminders to {} students", students.size());
        
        for (StudentDto student : students) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(student.getEmail());
                message.setSubject("Payment Reminder - Tutor Desk");
                
                String body = String.format(
                    "Dear %s,\n\n" +
                    "Please ensure your payment is processed to avoid any service interruptions.\n\n" +
                    "If you have any questions, please contact your teacher.\n\n" +
                    "Best regards,\nTutor Desk Team",
                    student.getName()
                );
                
                message.setText(body);
                mailSender.send(message);
                
                log.info("Payment reminder notification sent to: {}", student.getEmail());
            } catch (Exception e) {
                log.error("Failed to send payment reminder notification to: {}", student.getEmail(), e);
            }
        }
    }
    
    @Override
    public void sendWelcomeNotification(StudentDto student) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(student.getEmail());
            message.setSubject("Welcome to Tutor Desk!");

            String body = String.format(
                    "Dear %s,\n\n" +
                            "Welcome to Tutor Desk!\n\n" +
                            "Course Details:\n" +
                            "- Level: %s\n" +
                            "- Start Date: %s\n" +
                            "- Monthly Fee: $%.2f\n" +
                            "- Payment Day: %d\n\n" +
                            "Your subscription will expire on %s.\n\n" +
                            "If you have any questions, please don't hesitate to contact your teacher.\n\n" +
                            "Best regards,\nTutor Desk Team",
                    student.getName(),
                    student.getLevel() != null ? student.getLevel() : "Not specified",
                    student.getStartDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                    student.getMonthlyFee(),
                    student.getPaymentDay(),
                    student.getSubscriptionExpiry().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
            );
            
            message.setText(body);
            mailSender.send(message);
            
            log.info("Welcome notification sent to: {}", student.getEmail());
        } catch (Exception e) {
            log.error("Failed to send welcome notification to: {}", student.getEmail(), e);
        }
    }
}
