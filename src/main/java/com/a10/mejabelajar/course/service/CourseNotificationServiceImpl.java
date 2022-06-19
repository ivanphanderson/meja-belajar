package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.model.CourseNotification;
import com.a10.mejabelajar.course.model.NotificationType;
import com.a10.mejabelajar.course.repository.CourseNotificationRepository;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseNotificationServiceImpl implements CourseNotificationService {

    public static final long HOUR = 3600L * 1000; // in milli-seconds.

    @Autowired
    StudentService studentService;

    @Autowired
    CourseNotificationRepository courseNotificationRepository;

    @Override
    public void handleCreateInformation(CourseInformation courseInformation) {
        List<Student> students = studentService.getStudentsByCourse(courseInformation.getCourse());
        var instant = Instant.now();
        var date = Date.from(instant);
        var newDate = new Date(date.getTime() + 7 * HOUR);
        for (Student student: students) {
            var courseNotification = new CourseNotification();
            courseNotification.setNotificationType(NotificationType.CREATE);
            courseNotification.setCourseInformation(courseInformation);
            courseNotification.setStudent(student);
            courseNotification.setCreatedAt(newDate);
            courseNotificationRepository.save(courseNotification);
        }
    }

    @Override
    public void handleUpdateInformation(CourseInformation courseInformation) {
        List<Student> students = studentService.getStudentsByCourse(courseInformation.getCourse());
        var instant = Instant.now();
        var date = Date.from(instant);
        var newDate = new Date(date.getTime() + 7 * HOUR);
        for (Student student: students) {
            var courseNotification = new CourseNotification();
            courseNotification.setNotificationType(NotificationType.UPDATE);
            courseNotification.setCourseInformation(courseInformation);
            courseNotification.setStudent(student);
            courseNotification.setCreatedAt(newDate);
            courseNotificationRepository.save(courseNotification);
        }

    }

    @Override
    public List<CourseNotification> getCourseNotificationByStudentAndCreatedAtIsGreaterThanEqual(
            Student student,
            Date date) {
        return courseNotificationRepository
                .findAllByStudentAndCreatedAtIsGreaterThanEqual(student, date);
    }

    @Override
    public List<CourseNotification> getCourseNotificationByStudentAndCreatedAtIsLessThan(
            Student student,
            Date date) {
        return courseNotificationRepository
                .findAllByStudentAndCreatedAtIsLessThan(student, date);
    }
}
