package com.a10.mejabelajar.course.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.TeacherRepository;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseType;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import com.a10.mejabelajar.course.repository.CourseRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;
    private CourseDataTransferObject courseDataTransferObject;
    private User user;
    private Teacher teacher;
    private Student student;

    private static final int COURSE_ID = 100;

    /**
     * Run this before run every single test.
     */
    @BeforeEach
    public void setUp() {
        user = new User();
        teacher = new Teacher();
        student = new Student();

        course = new Course();
        course.setId(COURSE_ID);
        course.setTeacher(teacher);
        course.setCourseType(CourseType.IPA);
        course.setCourseDuration(10);
        course.setArchived(false);
        course.setCourseName("Hello");
        course.setCourseDescription("Hai hai :D");

        courseDataTransferObject = new CourseDataTransferObject();
        courseDataTransferObject.setCourseName("course");
        courseDataTransferObject.setCourseType("IPA");
        courseDataTransferObject.setCourseDescription("Auto lulus UTBK");
        courseDataTransferObject.setCourseDuration("100");

    }

    @Test
    public void testCreateCourse() {
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);

        lenient().when(courseService.createCourse(courseDataTransferObject, user))
                .thenReturn(course);
    }

    @Test
    public void testUpdateCourse() {
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);

        courseService.createCourse(courseDataTransferObject, user);
        final String pastName = courseDataTransferObject.getCourseName();

        final String newName = "Fantasy";
        courseDataTransferObject.setCourseName(newName);
        course.setCourseName(newName);

        lenient().when(courseService.updateCourse(COURSE_ID, teacher, courseDataTransferObject))
                .thenReturn(course);
        Course courseResult =
                courseService.updateCourse(COURSE_ID, teacher, courseDataTransferObject);

        assertNotEquals(courseResult.getCourseName(), pastName);
    }

    @Test
    public void testArchiveCourse() {
        courseService.archiveCourseById(teacher, course);
        assertFalse(teacher.isHaveCourse());
        assertTrue(course.isArchived());
    }

    @Test
    public void testGetAllCourses() {
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);

        courseService.createCourse(courseDataTransferObject, user);
        assertNotNull(courseService.getCourses());
    }

    @Test
    public void testGetCourseById() {
        lenient().when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        var course1 = courseService.getCourseById(COURSE_ID);
        assertEquals(course1.getId(), COURSE_ID);
    }

    @Test
    public void testGetCourseByStudent() {
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        lenient().when(courseService.getCoursesByStudent(student)).thenReturn(courses);
        List<Course> coursesResult = courseService.getCoursesByStudent(student);
        assertNotNull(coursesResult);
    }

    @Test
    public void testGetCourseByTeacherAndStatus() {
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);

        courseService.createCourse(courseDataTransferObject, user);
        lenient().when(courseService.getCourseByTeacherAndStatus(teacher, false))
                .thenReturn(course);
        var courseResult = courseService.getCourseByTeacherAndStatus(teacher, false);
        assertNotNull(courseResult);
    }

    @Test
    public void testGetCourseByArchived() {
        List<Course> courses = new ArrayList<>();
        courses.add(course);

        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);

        courseService.createCourse(courseDataTransferObject, user);
        lenient().when(courseService.getCourseByArchived(false)).thenReturn(courses);
        var courseResult = courseService.getCourseByArchived(false);
        assertNotEquals(courseResult.size(), 0);
    }

}
