package com.a10.mejabelajar.course.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.model.CourseNotification;
import com.a10.mejabelajar.course.model.NotificationType;
import com.a10.mejabelajar.course.repository.CourseNotificationRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
class CourseNotificationServiceImplTest {

    @Mock
    StudentService studentService;

    @Mock
    CourseNotificationRepository courseNotificationRepository;

    @InjectMocks
    CourseNotificationServiceImpl courseNotificationService;

    private Student student;
    private Date date;
    private Course course;
    private CourseInformation courseInformation;
    private CourseNotification courseNotification;

    /**
     * Run this before run every single test.
     */
    @BeforeEach
    void setUp() {
        course = new Course();
        date = new Date();
        student = new Student();
        student.setId("0e40ad72-513l-4z1b-9235-b1450c002b8a");
        student.setLastNotifBtnClick(date);
        courseInformation = new CourseInformation();
        courseInformation.setCourse(course);

        courseNotification = new CourseNotification();
        courseNotification.setNotificationType(NotificationType.CREATE);
        courseNotification.setCreatedAt(date);
        courseNotification.setId(0);
        courseNotification.setStudent(student);
    }

    @Test
    void testNewNotificationAfterCreateInformation() {
        List<Student> students = new ArrayList<>();
        students.add(student);

        when(studentService.getStudentsByCourse(course)).thenReturn(students);

        courseNotificationService.handleCreateInformation(courseInformation);

        assertNotNull(courseNotificationRepository.findAll());
    }

    @Test
    void testNewNotificationAfterUpdateInformation() {
        List<Student> students = new ArrayList<>();
        students.add(student);

        when(studentService.getStudentsByCourse(course)).thenReturn(students);

        courseNotificationService.handleUpdateInformation(courseInformation);

        assertNotNull(courseNotificationRepository.findAll());
    }

    @Test
    void testGetCourseNotificationByStudentAndCreatedAtIsGreaterThanEqual() {
        List<CourseNotification> courseNotifications = new ArrayList<>();
        courseNotifications.add(courseNotification);
        lenient().when(courseNotificationService
                .getCourseNotificationByStudentAndCreatedAtIsGreaterThanEqual(student, date))
                .thenReturn(courseNotifications);
        List<CourseNotification> courseNotifications1 =
                courseNotificationService
                        .getCourseNotificationByStudentAndCreatedAtIsGreaterThanEqual(
                                student,
                                date
                        );
        assertNotEquals(0, courseNotifications1.size());

        var courseNotification1 = courseNotifications1.get(0);
        assertEquals(courseNotification.getId(), courseNotification1.getId());
        assertEquals(courseNotification.getNotificationType(),
                courseNotification1.getNotificationType());
        assertEquals(courseNotification.getNotificationType().toString(),
                courseNotification1.getNotificationType().toString());
        assertEquals(courseNotification.getCourseInformation(),
                courseNotification1.getCourseInformation());
        assertEquals(courseNotification.getStudent(), courseNotification1.getStudent());
        assertEquals(courseNotification.getCreatedAt(), courseNotification1.getCreatedAt());
    }

    @Test
    void testGetCourseNotificationByStudentAndCreatedAtIsLessThan() {
        List<CourseNotification> courseNotifications = new ArrayList<>();
        courseNotifications.add(courseNotification);
        lenient().when(courseNotificationService
                .getCourseNotificationByStudentAndCreatedAtIsLessThan(student, date))
                .thenReturn(courseNotifications);
        List<CourseNotification> courseNotifications1 =
                courseNotificationService
                        .getCourseNotificationByStudentAndCreatedAtIsLessThan(student, date);
        assertNotEquals(0, courseNotifications1.size());

        var courseNotification1 = courseNotifications1.get(0);
        assertEquals(courseNotification.getId(), courseNotification1.getId());
        assertEquals(courseNotification.getNotificationType(),
                courseNotification1.getNotificationType());
        assertEquals(courseNotification.getNotificationType().toString(),
                courseNotification1.getNotificationType().toString());
        assertEquals(courseNotification.getCourseInformation(),
                courseNotification1.getCourseInformation());
        assertEquals(courseNotification.getStudent(), courseNotification1.getStudent());
        assertEquals(courseNotification.getCreatedAt(), courseNotification1.getCreatedAt());
    }
}
