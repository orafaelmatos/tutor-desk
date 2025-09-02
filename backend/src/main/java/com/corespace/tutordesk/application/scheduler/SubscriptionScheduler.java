package com.corespace.tutordesk.application.scheduler;

import com.corespace.tutordesk.application.dto.StudentDto;
import com.corespace.tutordesk.application.service.NotificationService;
import com.corespace.tutordesk.application.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionScheduler {
    
    private final StudentService studentService;
    private final NotificationService notificationService;
    
    @Value("${app.notification.expiry-days-before:7}")
    private int expiryDaysBefore;
    
    // NOTE: To enable automated scheduling, add spring-boot-starter-scheduling dependency
    // @Scheduled(cron = "0 0 9 * * *") // Run daily at 9 AM
    public void checkSubscriptionExpiry() {
        log.info("Starting scheduled subscription expiry check");
        
        try {
            List<StudentDto> studentsWithExpiringSubscription = 
                studentService.getStudentsWithExpiringSubscription(expiryDaysBefore);
            
            if (!studentsWithExpiringSubscription.isEmpty()) {
                log.info("Found {} students with expiring subscriptions", studentsWithExpiringSubscription.size());
                notificationService.sendSubscriptionExpiryNotification(studentsWithExpiringSubscription);
            } else {
                log.info("No students with expiring subscriptions found");
            }
        } catch (Exception e) {
            log.error("Error during subscription expiry check", e);
        }
    }
    
    // NOTE: To enable automated scheduling, add spring-boot-starter-scheduling dependency
    // @Scheduled(cron = "0 0 8 * * *") // Run daily at 8 AM
    public void sendPaymentReminders() {
        log.info("Starting scheduled payment reminder check");
        
        try {
            // Get students whose payment day is today
            int today = java.time.LocalDate.now().getDayOfMonth();
            List<StudentDto> studentsForPaymentReminder = 
                studentService.getStudentsByStatus(com.corespace.tutordesk.domain.entity.Student.StudentStatus.ACTIVE);
            
            // Filter students whose payment day is today or tomorrow
            List<StudentDto> studentsToRemind = studentsForPaymentReminder.stream()
                .filter(student -> {
                    int paymentDay = student.getPaymentDay();
                    return paymentDay == today || paymentDay == today + 1;
                })
                .toList();
            
            if (!studentsToRemind.isEmpty()) {
                log.info("Sending payment reminders to {} students", studentsToRemind.size());
                notificationService.sendPaymentReminderNotification(studentsToRemind);
            } else {
                log.info("No payment reminders to send today");
            }
        } catch (Exception e) {
            log.error("Error during payment reminder check", e);
        }
    }
}
