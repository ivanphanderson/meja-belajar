package com.a10.mejabelajar.course.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.auth.service.UserService;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseNotification;
import com.a10.mejabelajar.course.service.CourseNotificationService;
import com.a10.mejabelajar.course.service.CourseService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@WebMvcTest(controllers = CourseNotificationController.class)
public class CourseNotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private CourseNotificationService courseNotificationService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String TEACHER_UNAME = "teacher";
    private static final String STUDENT_UNAME = "student";
    private static final int COURSE_ID = 100;
    private Teacher teacher;
    private User user;
    private Course course;
    private Student student;

    /**
     * Run this before run every single test.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        student = new Student();
        teacher = new Teacher();
        user = new User();
        course = new Course();
        course.setId(COURSE_ID);
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessCreateCourseGet() throws Exception {
        mockMvc.perform(get("/course/notification"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void notStudentCantAccessCourseNotification() throws Exception {
        user.setRole(Role.TEACHER);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseByTeacherAndStatus(teacher, false)).thenReturn(course);


        mockMvc.perform(get("/course/notification"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/course/"))
                .andExpect(MockMvcResultMatchers.flash()
                        .attribute("error", "The feature is available only for student"));
    }

    @Test
    @WithMockUser(username = STUDENT_UNAME, authorities = {"USER", "STUDENT"})
    public void studentCanAccessCourseNotification() throws Exception {
        user.setRole(Role.STUDENT);
        when(userService.getUserByUsername(STUDENT_UNAME)).thenReturn(user);
        when(studentService.getStudentByUser(user)).thenReturn(student);

        var courseNotification1 = new CourseNotification();
        var courseNotification2 = new CourseNotification();
        List<CourseNotification> courseNotifications1 = new ArrayList<>();
        courseNotifications1.add(courseNotification1);
        List<CourseNotification> courseNotifications2 = new ArrayList<>();
        courseNotifications2.add(courseNotification2);

        when(courseNotificationService
                .getCourseNotificationByStudentAndCreatedAtIsGreaterThanEqual(
                        any(Student.class),
                        any(Date.class)))
                .thenReturn(courseNotifications1);
        when(courseNotificationService
                .getCourseNotificationByStudentAndCreatedAtIsLessThan(
                        any(Student.class),
                        any(Date.class)))
                .thenReturn(courseNotifications2);


        mockMvc.perform(get("/course/notification"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("courseNotifications"))
                .andExpect(model().attributeExists("courseNotifications1"))
                .andExpect(view().name("course/courseNotification"));
    }
}
