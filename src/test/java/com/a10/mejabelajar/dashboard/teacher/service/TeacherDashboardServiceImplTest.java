package com.a10.mejabelajar.dashboard.teacher.service;

import com.a10.mejabelajar.auth.model.Teacher;

import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherDashboardServiceImplTest {
    @InjectMocks
    private TeacherDashboardServiceImpl dashboardService;

    @Mock
    private CourseRepository courseRepository;

    private Teacher teacher;
    private Course course;
    private static final int COURSE_ID = 1;

    @BeforeEach
    void setUp(){
        teacher = new Teacher();
        course = new Course();
        course.setId(COURSE_ID);
        course.setArchived(false);
        course.setTeacher(teacher);
        teacher.setHaveCourse(true);
    }

    @Test
    void testGetActiveCourse() {
        when(dashboardService.getActiveCourse(teacher)).thenReturn(course);
        var nCourse = dashboardService.getActiveCourse(teacher);
        assertEquals(COURSE_ID, nCourse.getId());
        verify(courseRepository, times(1)).findByTeacherAndArchived(teacher, false);
    }

    @Test
    void testGetArchivedCourse() {
        List<Course> courses = new ArrayList<>();
        courses.add(course);

        when(dashboardService.getArchivedCourse(teacher)).thenReturn(courses);
        var nCourses = dashboardService.getArchivedCourse(teacher);
        assertNotNull(nCourses);
        verify(courseRepository, times(1)).findAllByTeacherAndArchived(teacher, true);
    }
}
