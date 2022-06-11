package com.a10.mejabelajar.dashboard.student.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.dashboard.teacher.service.TeacherDashboardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentDashboardServiceImplTest {
    @InjectMocks
    private StudentDashboardServiceImpl dashboardService;

    @Mock
    private CourseRepository courseRepository;

    private Student student;
    private Course course;

    @BeforeEach
    void setUp(){
        student = new Student();
        course = new Course();
    }

    @Test
    void testGetArchivedCourse() {
        List<Course> courses = new ArrayList<>();
        courses.add(course);

        when(dashboardService.getTakenCourse(student)).thenReturn(courses);
        var nCourses = dashboardService.getTakenCourse(student);
        assertNotNull(nCourses);
        verify(courseRepository, times(1)).findAllByNewMurid(student);
    }

}
