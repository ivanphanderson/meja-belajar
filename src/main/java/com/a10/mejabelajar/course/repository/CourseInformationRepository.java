package com.a10.mejabelajar.course.repository;

import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseInformationRepository extends JpaRepository<CourseInformation, String> {
    List<CourseInformation> findByCourse(Course course);
}
