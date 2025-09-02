package com.corespace.tutordesk.application.service.impl;

import com.corespace.tutordesk.application.dto.CreateStudentRequest;
import com.corespace.tutordesk.application.dto.ProgressEntryDto;
import com.corespace.tutordesk.application.dto.StudentDto;
import com.corespace.tutordesk.application.service.StudentService;
import com.corespace.tutordesk.domain.entity.ProgressEntry;
import com.corespace.tutordesk.domain.entity.Student;
import com.corespace.tutordesk.infrastructure.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository studentRepository;
    
    @Override
    public StudentDto createStudent(CreateStudentRequest request) {
        log.info("Creating new student: {}", request.getName());
        
        // Check if email already exists
        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Student with email " + request.getEmail() + " already exists");
        }
        
        Student student = Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .startDate(request.getStartDate())
                .level(request.getLevel())
                .status(Student.StudentStatus.ACTIVE)
                .monthlyFee(request.getMonthlyFee())
                .paymentDay(request.getPaymentDay())
                .subscriptionExpiry(calculateSubscriptionExpiry(request.getStartDate()))
                .notes(request.getNotes())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        Student savedStudent = studentRepository.save(student);
        return mapToDto(savedStudent);
    }
    
    @Override
    public StudentDto getStudentById(String id) {
        log.info("Fetching student with id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return mapToDto(student);
    }
    
    @Override
    public List<StudentDto> getAllStudents() {
        log.info("Fetching all students");
        return studentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<StudentDto> getStudentsByStatus(Student.StudentStatus status) {
        log.info("Fetching students with status: {}", status);
        return studentRepository.findByStatus(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    
    @Override
    public StudentDto updateStudent(String id, CreateStudentRequest request) {
        log.info("Updating student with id: {}", id);
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        existingStudent.setName(request.getName());
        existingStudent.setEmail(request.getEmail());
        existingStudent.setPhone(request.getPhone());
        existingStudent.setStartDate(request.getStartDate());
        existingStudent.setLevel(request.getLevel());
        existingStudent.setMonthlyFee(request.getMonthlyFee());
        existingStudent.setPaymentDay(request.getPaymentDay());
        existingStudent.setNotes(request.getNotes());
        existingStudent.setUpdatedAt(LocalDateTime.now());
        
        Student updatedStudent = studentRepository.save(existingStudent);
        return mapToDto(updatedStudent);
    }
    
    @Override
    public void deleteStudent(String id) {
        log.info("Deleting student with id: {}", id);
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
    
    @Override
    public StudentDto addProgressEntry(String studentId, String topic, String description, Double grade, Double maxGrade, String comments) {
        log.info("Adding progress entry for student: {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        ProgressEntry progressEntry = ProgressEntry.builder()
                .date(LocalDate.now())
                .topic(topic)
                .description(description)
                .grade(grade)
                .maxGrade(maxGrade)
                .comments(comments)
                .createdAt(LocalDateTime.now())
                .build();
        
        if (student.getProgress() == null) {
            student.setProgress(List.of(progressEntry));
        } else {
            student.getProgress().add(progressEntry);
        }
        
        student.setUpdatedAt(LocalDateTime.now());
        Student updatedStudent = studentRepository.save(student);
        return mapToDto(updatedStudent);
    }
    
    @Override
    public List<StudentDto> getStudentsWithExpiringSubscription(int daysBeforeExpiry) {
        log.info("Fetching students with expiring subscription in {} days", daysBeforeExpiry);
        LocalDate expiryDate = LocalDate.now().plusDays(daysBeforeExpiry);
        return studentRepository.findActiveStudentsWithExpiringSubscription(expiryDate).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public void updateSubscriptionExpiry(String studentId, int monthsToAdd) {
        log.info("Updating subscription expiry for student: {} by {} months", studentId, monthsToAdd);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        LocalDate newExpiryDate = student.getSubscriptionExpiry().plusMonths(monthsToAdd);
        student.setSubscriptionExpiry(newExpiryDate);
        student.setUpdatedAt(LocalDateTime.now());
        
        studentRepository.save(student);
    }
    
    private LocalDate calculateSubscriptionExpiry(LocalDate startDate) {
        return startDate.plusMonths(1);
    }
    
    private StudentDto mapToDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .startDate(student.getStartDate())
                .level(student.getLevel())
                .status(student.getStatus())
                .monthlyFee(student.getMonthlyFee())
                .paymentDay(student.getPaymentDay())
                .subscriptionExpiry(student.getSubscriptionExpiry())
                .progress(student.getProgress() != null ? student.getProgress().stream()
                        .map(this::mapProgressToDto)
                        .collect(Collectors.toList()) : null)
                .notes(student.getNotes())
                .build();
    }
    
    private ProgressEntryDto mapProgressToDto(ProgressEntry progress) {
        return ProgressEntryDto.builder()
                .id(progress.hashCode() + "") // Simple ID generation for demo
                .date(progress.getDate())
                .topic(progress.getTopic())
                .description(progress.getDescription())
                .grade(progress.getGrade())
                .maxGrade(progress.getMaxGrade())
                .comments(progress.getComments())
                .build();
    }

}
