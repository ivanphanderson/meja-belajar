package com.a10.mejabelajar.course.repository;

import com.a10.mejabelajar.course.model.Course;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
}
