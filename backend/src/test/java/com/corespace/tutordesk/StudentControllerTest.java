package com.corespace.tutordesk;



import com.corespace.tutordesk.application.dto.CreateStudentRequest;
import com.corespace.tutordesk.application.dto.StudentDto;
import com.corespace.tutordesk.application.service.impl.NotificationServiceImpl;
import com.corespace.tutordesk.application.service.impl.StudentServiceImpl;
import com.corespace.tutordesk.infrastructure.controller.StudentController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentServiceImpl studentService;

    @MockBean
    private NotificationServiceImpl notificationService;

    @Test
    void testCreateStudent() throws Exception {
        CreateStudentRequest request = new CreateStudentRequest(
                "John Doe", "john@example.com", "123456789", LocalDate.now(),
                "Beginner", 100.0, 1, "Notes"
        );

        StudentDto response = StudentDto.builder()
                .id("1")
                .name(request.getName())
                .email(request.getEmail())
                .build();

        when(studentService.createStudent(any(CreateStudentRequest.class))).thenReturn(response);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(notificationService, times(1)).sendWelcomeNotification(response);
    }

    @Test
    void testGetStudentById() throws Exception {
        StudentDto student = StudentDto.builder()
                .id("1")
                .name("John Doe")
                .email("john@example.com")
                .build();

        when(studentService.getStudentById("1")).thenReturn(student);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetAllStudents() throws Exception {
        StudentDto student1 = StudentDto.builder().id("1").name("John").build();
        StudentDto student2 = StudentDto.builder().id("2").name("Jane").build();

        when(studentService.getAllStudents()).thenReturn(List.of(student1, student2));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent("1");

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isNoContent());

        verify(studentService, times(1)).deleteStudent("1");
    }

    @Test
    void testUpdateStudent() throws Exception {
        CreateStudentRequest request = new CreateStudentRequest(
                "John Updated", "john@example.com", "123456789", LocalDate.now(),
                "Intermediate", 120.0, 5, "Updated Notes"
        );

        StudentDto updated = StudentDto.builder()
                .id("1")
                .name("John Updated")
                .email("john@example.com")
                .build();

        when(studentService.updateStudent(eq("1"), any(CreateStudentRequest.class))).thenReturn(updated);

        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));
    }
}
