package com.a10.mejabelajar.dashboard.student.controller;

import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.UserRepository;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.dashboard.student.service.StudentDashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = StudentDashboardController.class)
class StudentDashboardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentDashboardService dashboardService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Authentication authentication;
    private SecurityContext securityContext;
    private User user;
    private Student student;
    private static final String MOCK_USERNAME = "user1";
    private static final String MOCK_EMAIL = "user1@mail.com";

    @BeforeEach
    void setUp() {
        authentication = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User();
        user.setRole(Role.STUDENT);
        user.setUsername(MOCK_USERNAME);
        user.setEmail(MOCK_EMAIL);

        student = new Student();
        student.setUser(user);
    }

    @Test
    @WithAnonymousUser
    void notLoggedInCantAccessStudentashboard() throws Exception {
        mockMvc.perform(get("/dashboard/student/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "ADMIN"})
    void adminCantAccessStudentDashboard() throws Exception {
        mockMvc.perform(get("/dashboard/student/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "TEACHER"})
    void teacherCantAccessStudentDashboard() throws Exception {
        mockMvc.perform(get("/dashboard/student/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "STUDENT"})
    void studentCanAccessStudentDashboard() throws Exception {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        mockMvc.perform(get("/dashboard/student/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "STUDENT"})
    void testStudentDashboard() throws Exception {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        mockMvc.perform(get("/dashboard/student/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("takenCourses"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("email"));
    }
}
