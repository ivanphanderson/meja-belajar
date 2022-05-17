package com.a10.mejabelajar.course.repository;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.course.model.CourseNotification;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseNotificationRepository extends JpaRepository<CourseNotification, String> {

    List<CourseNotification>
        findAllByStudentAndCreatedAtIsGreaterThanEqual(Student student, Date date);

    List<CourseNotification>
        findAllByStudentAndCreatedAtIsLessThan(Student student, Date date);
}
