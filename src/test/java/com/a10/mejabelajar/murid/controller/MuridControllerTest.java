package com.a10.mejabelajar.murid.controller;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.auth.service.UserService;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.course.service.CourseService;
import com.a10.mejabelajar.murid.service.MuridService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MuridController.class)
class MuridControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private MuridService muridService;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String STUDENT_UNAME = "student";

    private User user;
    private Course course;
    private Student student;

    /**
     * Run this before run every single test.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        student = new Student();
        user = new User();
        course = new Course();
        course.setId(0);
    }

    @Test
    @WithAnonymousUser
    void notLoggedInCantAccessCreateCourseGet() throws Exception {
        mockMvc.perform(get("/murid"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = STUDENT_UNAME, authorities = {"USER", "STUDENT"})
    void studentCanAccessReadCourse() throws Exception {
        List<Course> newCourse = new ArrayList<>();
        newCourse.add(course);
        when(userService.getUserByUsername(STUDENT_UNAME)).thenReturn(user);
        when(courseService.getCourseByArchived(false)).thenReturn(newCourse);
        when(studentService.getStudentByUser(any(User.class))).thenReturn(student);
        mockMvc.perform(get("/murid"))
                .andExpect(status().isOk());
    }
}
