package com.a10.mejabelajar.auth.repository;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findById(String id);

    Student findByUser(User user);

    List<Student> findAllByNewCourse(Course course);
}
