package com.a10.mejabelajar.auth.repository;

import com.a10.mejabelajar.auth.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findById(String id);
    Student findByUserId(String id);
}
