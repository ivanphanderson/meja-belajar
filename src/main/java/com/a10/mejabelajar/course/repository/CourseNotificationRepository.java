package com.a10.mejabelajar.course.repository;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.model.CourseNotification;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CourseNotificationRepository extends JpaRepository<CourseNotification, String> {

    List<CourseNotification>
        findAllByStudentAndCreatedAtIsGreaterThanEqual(Student student, Date date);

    List<CourseNotification>
        findAllByStudentAndCreatedAtIsLessThan(Student student, Date date);

    List<CourseNotification> findAllByCourseInformation(CourseInformation courseInformation);

    @Modifying
    @Query(value = "DELETE FROM CourseNotification C where C.id = ?1")
    void deleteCourseNotificationById(int id);
}
