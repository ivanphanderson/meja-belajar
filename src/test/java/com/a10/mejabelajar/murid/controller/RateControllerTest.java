package com.a10.mejabelajar.murid.controller;

import com.a10.mejabelajar.auth.repository.UserRepository;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.murid.model.Rate;
import com.a10.mejabelajar.murid.service.RateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RateController.class)
class RateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RateService rateService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String STUDENT_UNAME = "student";

    private Course course;
    private Rate rate;

    /**
     * Run this before run every single test.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        course = new Course();
        rate = new Rate();
        course.setId(0);
    }

    @Test
    @WithMockUser(username = STUDENT_UNAME, authorities = {"USER", "STUDENT"})
    void studentCanRateCourse() throws Exception {
        when(rateService.getByIdStudentAndIdCourse(anyString(),anyInt())).thenReturn(rate);
        mockMvc.perform(post("/course/rate"))
                .andExpect(status().is3xxRedirection());
        when(rateService.getByIdStudentAndIdCourse(anyString(),anyInt())).thenReturn(null);
        mockMvc.perform(post("/course/rate"))
                .andExpect(status().is3xxRedirection());

    }
}