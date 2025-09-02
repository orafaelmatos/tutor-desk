package com.corespace.tutordesk.infrastructure.repository;

import com.corespace.tutordesk.domain.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    
    Optional<Student> findByEmail(String email);
    
    List<Student> findByStatus(Student.StudentStatus status);

    @Query("{'subscription_expiry': {$lte: ?0}}")
    List<Student> findStudentsWithExpiringSubscription(LocalDate expiryDate);
    
    @Query("{'subscription_expiry': {$lte: ?0}, 'status': 'ACTIVE'}")
    List<Student> findActiveStudentsWithExpiringSubscription(LocalDate expiryDate);
}
