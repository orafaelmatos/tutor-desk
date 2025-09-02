package com.corespace.tutordesk.application.service;

import com.corespace.tutordesk.application.dto.CreateStudentRequest;
import com.corespace.tutordesk.application.dto.StudentDto;
import com.corespace.tutordesk.domain.entity.Student;

import java.util.List;

public interface StudentService {
    
    StudentDto createStudent(CreateStudentRequest request);
    
    StudentDto getStudentById(String id);
    
    List<StudentDto> getAllStudents();
    
    List<StudentDto> getStudentsByStatus(Student.StudentStatus status);

    StudentDto updateStudent(String id, CreateStudentRequest request);
    
    void deleteStudent(String id);
    
    StudentDto addProgressEntry(String studentId, String topic, String description, Double grade, Double maxGrade, String comments);
    
    List<StudentDto> getStudentsWithExpiringSubscription(int daysBeforeExpiry);
    
    void updateSubscriptionExpiry(String studentId, int monthsToAdd);
}
