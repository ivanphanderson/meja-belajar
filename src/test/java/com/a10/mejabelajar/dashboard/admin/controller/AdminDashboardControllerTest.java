package com.a10.mejabelajar.dashboard.admin.controller;

import com.a10.mejabelajar.admin.service.ActivationService;
import com.a10.mejabelajar.admin.service.LogService;
import com.a10.mejabelajar.auth.model.Admin;
import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.UserRepository;
import com.a10.mejabelajar.dashboard.admin.service.AdminDashboardService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminDashboardController.class)
class AdminDashboardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogService logService;

    @MockBean
    private AdminDashboardService dashboardService;

    @MockBean
    private ActivationService activationService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Admin admin;
    private User user;
    private static final String MOCK_USERNAME = "user1";
    private static final String MOCK_EMAIL = "user1@mail.com";
    private static final String MOCK_TOKEN = "abc";
    private Authentication authentication;
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        authentication = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        admin = new Admin();
        user = new User();
        user.setRole(Role.ADMIN);
        user.setUsername(MOCK_USERNAME);
        user.setEmail(MOCK_EMAIL);
        admin.setUser(user);
    }

    @Test
    @WithAnonymousUser
    void notLoggedInCantAccessAdminDashboard() throws Exception {
        mockMvc.perform(get("/dashboard/admin/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "TEACHER"})
    void teacherCantAccessAdminDashboard() throws Exception {
        mockMvc.perform(get("/dashboard/admin/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "STUDENT"})
    void studentCantAccessAdminDashboard() throws Exception {
        mockMvc.perform(get("/dashboard/admin/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "ADMIN"})
    void adminCanAccessAdminDashboard() throws Exception {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        mockMvc.perform(get("/dashboard/admin/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "ADMIN"})
    void testAdminDashboard() throws Exception {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        mockMvc.perform(get("/dashboard/admin/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("role"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("email"))
                .andExpect(model().attributeExists("logs"))
                .andExpect(model().attributeExists("dto"));
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, authorities = {"USER", "ADMIN"})
    void testGenerateToken() throws Exception {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        mockMvc.perform(post("/dashboard/admin/generate-token")
                        .param("token", MOCK_TOKEN))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(AdminDashboardController.class))
                .andExpect(handler().methodName("generateToken"));
    }
}
