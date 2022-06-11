package com.a10.mejabelajar.auth.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.a10.mejabelajar.auth.exception.UsernameOrEmailAlreadyUsedException;
import com.a10.mejabelajar.auth.model.CreateAdminDTO;
import com.a10.mejabelajar.auth.model.CreateStudentAndTeacherDTO;
import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.UserRepository;
import com.a10.mejabelajar.auth.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(controllers = RegistrationController.class)
class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    private static final String MOCK_USERNAME = "user";
    private static final String MOCK_EMAIL = "user@mail.com";
    private static final String MOCK_PASSWORD = "abc123";
    private static final Role MOCK_ROLE = Role.STUDENT;
    private static final String MOCK_TOKEN = "abcdef";

    private User mockStudent;
    private User mockAdmin;
    private CreateStudentAndTeacherDTO studentDTO;
    private CreateAdminDTO adminDTO;

    @BeforeEach
    void setUp() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(MOCK_PASSWORD);
        mockStudent = new User(MOCK_USERNAME, MOCK_EMAIL, encodedPassword, MOCK_ROLE);
        mockAdmin = new User(MOCK_USERNAME, MOCK_EMAIL, encodedPassword, Role.ADMIN);

        studentDTO = new CreateStudentAndTeacherDTO();
        studentDTO.setUsername(MOCK_USERNAME);
        studentDTO.setEmail(MOCK_EMAIL);
        studentDTO.setPassword(MOCK_PASSWORD);
        studentDTO.setRole(MOCK_ROLE.name());

        adminDTO = new CreateAdminDTO();
        adminDTO.setUsername(MOCK_USERNAME);
        adminDTO.setEmail(MOCK_EMAIL);
        adminDTO.setPassword(MOCK_PASSWORD);
        adminDTO.setToken(MOCK_TOKEN);
    }

    @Test
    void testGetRegistrationPage() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dto"))
                .andExpect(view().name("auth/registration"))
                .andExpect(handler().methodName("getRegisterPage"));
    }

    @Test
    void testRegisterStudent() throws Exception {
        when(registrationService.createUser(studentDTO)).thenReturn(mockStudent);
        mockMvc.perform(post("/signup")
                        .param("username", MOCK_USERNAME)
                        .param("email", MOCK_EMAIL)
                        .param("password", MOCK_PASSWORD)
                        .param("role", MOCK_ROLE.name()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("success"))
                .andExpect(handler().methodName("registerUser"));
    }

    @Test
    void testGetAdminRegistrationPage() throws Exception {
        mockMvc.perform(get("/signup/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dto"))
                .andExpect(view().name("auth/adminRegistration"))
                .andExpect(handler().methodName("getAdminRegisterPage"));
    }

    @Test
    void testRegisterAdmin() throws Exception {
        when(registrationService.createUser(adminDTO)).thenReturn(mockAdmin);
        mockMvc.perform(post("/signup/admin")
                        .param("username", MOCK_USERNAME)
                        .param("email", MOCK_EMAIL)
                        .param("password", MOCK_PASSWORD)
                        .param("token", MOCK_TOKEN))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("success"))
                .andExpect(handler().methodName("registerAdmin"));
    }

    @Test
    void testFailedRegistration() throws Exception {
        doThrow(new UsernameOrEmailAlreadyUsedException("Username already used")).when(registrationService).createUser(studentDTO);
        mockMvc.perform(post("/signup")
                        .param("username", MOCK_USERNAME)
                        .param("email", MOCK_EMAIL)
                        .param("password", MOCK_PASSWORD)
                        .param("role", MOCK_ROLE.name()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(handler().methodName("registerUser"));
    }

    @Test
    void testFailedAdminRegistration() throws Exception {
        doThrow(new UsernameOrEmailAlreadyUsedException("Username already used")).when(registrationService).createUser(adminDTO);
        mockMvc.perform(post("/signup/admin")
                        .param("username", MOCK_USERNAME)
                        .param("email", MOCK_EMAIL)
                        .param("password", MOCK_PASSWORD)
                        .param("token", MOCK_TOKEN))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(handler().methodName("registerAdmin"));
    }
}
