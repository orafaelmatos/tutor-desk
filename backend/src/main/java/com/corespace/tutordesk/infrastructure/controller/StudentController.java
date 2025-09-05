package com.corespace.tutordesk.infrastructure.controller;

import com.corespace.tutordesk.application.dto.CreateStudentRequest;
import com.corespace.tutordesk.application.dto.StudentDto;
import com.corespace.tutordesk.application.service.NotificationService;
import com.corespace.tutordesk.application.service.StudentService;
import com.corespace.tutordesk.domain.entity.Student;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Student Management", description = "APIs for managing students")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    
    private final StudentService studentService;
    private final NotificationService notificationService;
    
    @PostMapping
    @Operation(summary = "Create a new student", description = "Register a new student with basic information")
    public ResponseEntity<StudentDto> createStudent(
            @Valid @RequestBody CreateStudentRequest request) {
        log.info("Creating new student: {}", request.getName());
        StudentDto createdStudent = studentService.createStudent(request);
        
        // Send welcome notification
        notificationService.sendWelcomeNotification(createdStudent);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieve student information by their unique ID")
    public ResponseEntity<StudentDto> getStudentById(
            @Parameter(description = "Student ID") @PathVariable String id) {
        log.info("Fetching student with id: {}", id);
        StudentDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    
    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve a list of all registered students")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        log.info("Fetching all students");
        List<StudentDto> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get students by status", description = "Retrieve students filtered by their current status")
    public ResponseEntity<List<StudentDto>> getStudentsByStatus(
            @Parameter(description = "Student status") @PathVariable Student.StudentStatus status) {
        log.info("Fetching students with status: {}", status);
        List<StudentDto> students = studentService.getStudentsByStatus(status);
        return ResponseEntity.ok(students);
    }

    
    @PutMapping("/{id}")
    @Operation(summary = "Update student", description = "Update existing student information")
    public ResponseEntity<StudentDto> updateStudent(
            @Parameter(description = "Student ID") @PathVariable String id,
            @Valid @RequestBody CreateStudentRequest request) {
        log.info("Updating student with id: {}", id);
        StudentDto updatedStudent = studentService.updateStudent(id, request);
        return ResponseEntity.ok(updatedStudent);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Remove a student from the system")
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "Student ID") @PathVariable String id) {
        log.info("Deleting student with id: {}", id);
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/progress")
    @Operation(summary = "Add progress entry", description = "Add a new progress entry for a student")
    public ResponseEntity<StudentDto> addProgressEntry(
            @Parameter(description = "Student ID") @PathVariable String id,
            @RequestParam String topic,
            @RequestParam String description,
            @RequestParam Double grade,
            @RequestParam Double maxGrade,
            @RequestParam(required = false) String comments) {
        log.info("Adding progress entry for student: {}", id);
        StudentDto updatedStudent = studentService.addProgressEntry(id, topic, description, grade, maxGrade, comments);
        return ResponseEntity.ok(updatedStudent);
    }
    
    @GetMapping("/expiring")
    @Operation(summary = "Get students with expiring subscription", description = "Retrieve students whose subscription is expiring soon")
    public ResponseEntity<List<StudentDto>> getStudentsWithExpiringSubscription(
            @Parameter(description = "Days before expiry") @RequestParam(defaultValue = "7") int daysBeforeExpiry) {
        log.info("Fetching students with expiring subscription in {} days", daysBeforeExpiry);
        List<StudentDto> students = studentService.getStudentsWithExpiringSubscription(daysBeforeExpiry);
        return ResponseEntity.ok(students);
    }
    
    @PutMapping("/{id}/subscription")
    @Operation(summary = "Extend subscription", description = "Extend a student's subscription by specified months")
    public ResponseEntity<Void> extendSubscription(
            @Parameter(description = "Student ID") @PathVariable String id,
            @Parameter(description = "Months to add") @RequestParam int monthsToAdd) {
        log.info("Extending subscription for student: {} by {} months", id, monthsToAdd);
        studentService.updateSubscriptionExpiry(id, monthsToAdd);
        return ResponseEntity.ok().build();
    }
}
