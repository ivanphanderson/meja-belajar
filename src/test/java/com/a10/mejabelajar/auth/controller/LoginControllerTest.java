package com.a10.mejabelajar.auth.controller;

import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.UserRepository;
import com.a10.mejabelajar.auth.service.RegistrationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    private static final String MOCK_PASSWORD = "abc123";

    private static final String MOCK_USERNAME_1 = "user1";
    private static final String MOCK_EMAIL_1 = "user1@mail.com";
    private static final Role MOCK_STUDENT_ROLE = Role.STUDENT;

    private static final String MOCK_USERNAME_2 = "user2";
    private static final String MOCK_EMAIL_2 = "user2@mail.com";
    private static final Role MOCK_TEACHER_ROLE = Role.TEACHER;

    private static final String MOCK_USERNAME_3 = "user3";
    private static final String MOCK_EMAIL_3 = "user3@mail.com";
    private static final Role MOCK_ADMIN_ROLE = Role.ADMIN;

    private User mockUser1;
    private User mockUser2;
    private User mockUser3;

    @BeforeEach
    void setUp(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(MOCK_PASSWORD);

        mockUser1 = new User(MOCK_USERNAME_1, MOCK_EMAIL_1, encodedPassword, MOCK_STUDENT_ROLE);
        mockUser2 = new User(MOCK_USERNAME_2, MOCK_EMAIL_2, encodedPassword, MOCK_TEACHER_ROLE);
        mockUser3 = new User(MOCK_USERNAME_3, MOCK_EMAIL_3, encodedPassword, MOCK_ADMIN_ROLE);
    }

    @Test
    void testGetLoginPage() throws Exception{
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"))
                .andExpect(handler().methodName("getLoginPage"));
    }

    @Test
    void testLoginForStudent() throws Exception{
        when(userDetailsService.loadUserByUsername(MOCK_USERNAME_1)).thenReturn(mockUser1);
        mockMvc.perform(post("/login")
                        .param("username", MOCK_USERNAME_1)
                        .param("password", MOCK_PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/student/"));
    }

    @Test
    void testLoginForTeacher() throws Exception{
        when(userDetailsService.loadUserByUsername(MOCK_USERNAME_2)).thenReturn(mockUser2);
        mockMvc.perform(post("/login")
                        .param("username", MOCK_USERNAME_2)
                        .param("password", MOCK_PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/teacher/"));
    }

    @Test
    void testLoginForAdmin() throws Exception{
        when(userDetailsService.loadUserByUsername(MOCK_USERNAME_3)).thenReturn(mockUser3);
        mockMvc.perform(post("/login")
                        .param("username", MOCK_USERNAME_3)
                        .param("password", MOCK_PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/admin/"));
    }

    @Test
    void testFailedLogin() throws Exception{
        when(userDetailsService.loadUserByUsername(MOCK_USERNAME_1)).thenReturn(mockUser1);
        mockMvc.perform(post("/login")
                        .param("username", MOCK_USERNAME_1)
                        .param("password", "abc"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=true"));
    }
}
