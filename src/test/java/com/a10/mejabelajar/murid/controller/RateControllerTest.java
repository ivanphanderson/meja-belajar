package com.a10.mejabelajar.murid.controller;

import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.UserRepository;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.murid.core.PrincipalFactory;
import com.a10.mejabelajar.murid.model.Rate;
import com.a10.mejabelajar.murid.service.RateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RateController.class)
class RateControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RateService rateService;

    @InjectMocks
    private RateController rateController;

    @Mock
    private PrincipalFactory principalFactory;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String STUDENT_UNAME = "student";

    private Course course;
    private Rate rate;
    private User user;

    /**
     * Run this before run every single test.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        course = new Course();
        rate = new Rate();
        user = new User();
        course.setId(0);
        user.setId("as");
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(rateController).build();
    }

    @Test
    //@WithMockUser(username = STUDENT_UNAME, authorities = {"USER", "STUDENT"})
    void studentCanRateCourse() throws Exception {
        rateController.setPrincipalFactory(principalFactory);
        when(principalFactory.getPrincipal()).thenReturn(user);
        when(rateService.getByIdStudentAndIdCourse(anyString(),anyInt())).thenReturn(rate);
        mockMvc.perform(post("/course/rate").param("rate","1").param("idCourse","120"))
                .andExpect(status().is3xxRedirection());
        when(rateService.getByIdStudentAndIdCourse(anyString(),anyInt())).thenReturn(null);
        mockMvc.perform(post("/course/rate").param("rate","1").param("idCourse","120"))
                .andExpect(status().is3xxRedirection());

    }
}