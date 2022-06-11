package com.a10.mejabelajar.dashboard.teacher.controller;

import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.UserRepository;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.dashboard.teacher.service.TeacherDashboardService;
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

@WebMvcTest(controllers = TeacherDashboardController.class)
class TeacherDashboardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherDashboardService dashboardService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Authentication authentication;
    private SecurityContext securityContext;
    private User user;
    private Teacher teacher;
    private static final String MOCK_USERNAME = "user1";
    private static final String MOCK_EMAIL = "user1@mail.com";

    @BeforeEach
    void setUp() {
        authentication = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User();
        user.setRole(Role.TEACHER);
        user.setUsername(MOCK_USERNAME);
        user.setEmail(MOCK_EMAIL);

        teacher = new Teacher();
        teacher.setUser(user);
    }

    @Test
    @WithAnonymousUser
    void notLoggedInCantAccessTeacherDashboard() throws Exception {
        mockMvc.perform(get("/dashboard/teacher/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "ADMIN"})
    void adminCantAccessTeacherDashboard() throws Exception {
        mockMvc.perform(get("/dashboard/teacher/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "STUDENT"})
    void studentCantAccessTeacherDashboard() throws Exception {
        mockMvc.perform(get("/dashboard/teacher/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "TEACHER"})
    void teacherCanAccessTeacherDashboard() throws Exception {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        mockMvc.perform(get("/dashboard/teacher/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "TEACHER"})
    void testTeacherDashboard() throws Exception {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        Mockito.when(dashboardService.getActiveCourse(teacher)).thenReturn(new Course());

        mockMvc.perform(get("/dashboard/teacher/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activeCourse"))
                .andExpect(model().attributeExists("archivedCourses"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("email"));
    }


}
