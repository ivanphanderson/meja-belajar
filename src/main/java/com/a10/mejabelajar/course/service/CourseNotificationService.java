package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.model.CourseNotification;
import java.util.Date;
import java.util.List;

public interface CourseNotificationService {
    void handleCreateInformation(CourseInformation courseInformation);

    void handleUpdateInformation(CourseInformation courseInformation);

    List<CourseNotification> getCourseNotificationByStudentAndCreatedAtIsGreaterThanEqual(
            Student student,
            Date date
    );

    List<CourseNotification> getCourseNotificationByStudentAndCreatedAtIsLessThan(
            Student student,
            Date date
    );
}
