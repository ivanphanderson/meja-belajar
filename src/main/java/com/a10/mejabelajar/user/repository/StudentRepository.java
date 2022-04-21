package com.a10.mejabelajar.user.repository;

import com.a10.mejabelajar.user.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
