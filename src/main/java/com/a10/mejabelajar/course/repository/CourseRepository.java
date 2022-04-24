package com.a10.mejabelajar.course.repository;

import com.a10.mejabelajar.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Course findById(int id);
}
