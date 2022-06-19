package com.a10.mejabelajar.admin.controller;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.admin.service.ActivationService;
import com.a10.mejabelajar.admin.service.LogService;
import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.auth.service.UserService;
import com.a10.mejabelajar.course.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private UserService userService;

    @MockBean
    private ActivationService activationService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private LogService logService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String TEACHER_UNAME = "teacher";
    private static final String STUDENT_UNAME = "student";
    private static final String ID = "100";
    private static final String TIME = "2022-06-11'T'10:00";

    @Mock
    private Teacher teacher;

    @Mock
    private User user;

    @Mock
    private Student student;

    @Mock
    private Log log;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    void teacherCanAccessFormLogGet() throws Exception {
        List<Student> students = List.of(student);
        when(studentService.getStudents()).thenReturn(students);
        when(student.getUser()).thenReturn(user);
        when(student.getUser().getUsername()).thenReturn(STUDENT_UNAME);
        mockMvc.perform(get("/admin/form-log"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attributeExists("newLog"))
                .andExpect(view().name("admin/formLog"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    void teacherCanAccessFormLogPostSuccess() throws Exception {
        List<Student> students = List.of(student);
        when(studentService.getStudentById(anyString())).thenReturn(student);
        when(teacherService.getTeacherByUser(any(User.class))).thenReturn(teacher);
        when(studentService.getStudents()).thenReturn(students);
        when(logService.countDuration(anyString(), anyString())).thenReturn("3 jam");
        when(logService.createLog(TIME, TIME, "3 jam", "deskripsi", student, teacher)).thenReturn(log);
        mockMvc.perform(post("/admin/form-log?start=xx&end=yy&desc=1&studentId=asd"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/logs"));
    }


}
