package com.a10.mejabelajar.course.repository;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.course.model.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Course findById(int id);

    Course findByTeacherAndArchived(Teacher teacher, boolean archived);

    List<Course> findAllByNewMurid(Student student);
}
