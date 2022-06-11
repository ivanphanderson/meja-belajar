package com.a10.mejabelajar.course.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
class CourseServiceImplTest {

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
    void setUp() {
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
        courseDataTransferObject.setCourseName("Hello");
        courseDataTransferObject.setCourseType("IPA");
        courseDataTransferObject.setCourseDescription("Hai hai :D");
        courseDataTransferObject.setCourseDuration("10");

    }

    @Test
    void testCreateCourse() {
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);

        lenient().when(courseService.createCourse(courseDataTransferObject, user))
                .thenReturn(course);

        assertNotNull(courseService.createCourse(courseDataTransferObject, user));
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testUpdateCourse() {
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

        assertNotEquals(pastName, courseResult.getCourseName());
        verify(courseRepository, times(1)).save(courseResult);
    }

    @Test
    void testArchiveCourse() {
        courseService.archiveCourseById(teacher, course);

        assertFalse(teacher.isHaveCourse());
        assertTrue(course.isArchived());
        verify(courseRepository, times(1)).save(course);
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void testGetAllCourses() {
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);

        courseService.createCourse(courseDataTransferObject, user);
        assertNotNull(courseService.getCourses());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testGetCourseById() {
        lenient().when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        var course1 = courseService.getCourseById(COURSE_ID);
        assertEquals(COURSE_ID, course1.getId());
        verify(courseRepository, times(1)).findById(COURSE_ID);
    }

    @Test
    void testGetCourseByStudent() {
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        lenient().when(courseService.getCoursesByStudent(student)).thenReturn(courses);
        List<Course> coursesResult = courseService.getCoursesByStudent(student);
        assertNotNull(coursesResult);
        verify(courseRepository, times(1)).findAllByNewMurid(student);
    }

    @Test
    void testGetCourseByTeacherAndStatus() {
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);

        courseService.createCourse(courseDataTransferObject, user);
        lenient().when(courseService.getCourseByTeacherAndStatus(teacher, false))
                .thenReturn(course);
        var courseResult = courseService.getCourseByTeacherAndStatus(teacher, false);
        assertNotNull(courseResult);
        verify(courseRepository, times(1)).findByTeacherAndArchived(teacher, false);
    }

    @Test
    void testGetCourseByArchived() {
        List<Course> courses = new ArrayList<>();
        courses.add(course);

        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);

        courseService.createCourse(courseDataTransferObject, user);
        lenient().when(courseService.getCourseByArchived(false)).thenReturn(courses);
        var courseResult = courseService.getCourseByArchived(false);
        assertNotEquals(0, courseResult.size());

        verify(courseRepository, times(1)).findAllByArchived(false);
    }

}
