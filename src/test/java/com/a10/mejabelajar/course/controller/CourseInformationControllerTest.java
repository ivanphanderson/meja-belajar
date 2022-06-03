package com.a10.mejabelajar.course.controller;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.auth.service.UserService;
import com.a10.mejabelajar.course.exception.CourseInformationInvalidException;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.service.CourseInformationService;
import com.a10.mejabelajar.course.service.CourseNotificationService;
import com.a10.mejabelajar.course.service.CourseService;
import com.a10.mejabelajar.murid.service.RateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseInformationController.class)
public class CourseInformationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private CourseInformationService courseInformationService;

    @MockBean
    private CourseNotificationService courseNotificationService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Mock
    private CourseInformation courseInformation;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String TEACHER_UNAME = "teacher";
    private static final String STUDENT_UNAME = "student";
    private static final int COURSE_ID = 100;
    private static final int COURSE_INFORMATION_ID = 101;
    private Teacher teacher;
    private User user;
    private Course course;
    private Student student;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        student = new Student();
        teacher = new Teacher();
        user = new User();
        course = new Course();
        course.setId(COURSE_ID);

        courseInformation.setCreatedAt(new Date());
        courseInformation.setUpdatedAt(new Date());
        courseInformation.setCourse(course);
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessCreateCourseInformationGet() throws Exception {
        mockMvc.perform(get("/course/information/create/" + COURSE_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));;
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCanAccessCreateCourseInformationGetWhenItsValid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);

        mockMvc.perform(get("/course/information/create/" + COURSE_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("courseId"))
                .andExpect(model().attributeExists("courseInformation"))
                .andExpect(view().name("course/createCourseInformation"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantAccessCreateCourseInformationGetWhenItsInvalid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(true);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseService.getCourseByTeacherAndStatus(teacher, false)).thenReturn(course);

        mockMvc.perform(get("/course/information/create/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());


        course.setArchived((false));
        var teacher1 = new Teacher();
        course.setTeacher(teacher1);
        mockMvc.perform(get("/course/information/create/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());

        teacher.setHaveCourse(false);
        mockMvc.perform(get("/course/information/create/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessCreateCourseInformationPost() throws Exception {
        mockMvc.perform(post("/course/information/create/" + COURSE_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));;
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCanCreateCourseInformationPostWhenItsValid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseInformationService.createCourseInformation(courseInformation)).thenReturn(courseInformation);

        mockMvc.perform(post("/course/information/create/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantCreateCourseInformationPostWhenItHasInvalidAttr() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseInformationService.createCourseInformation(any(CourseInformation.class))).thenThrow(new CourseInformationInvalidException("Required Title"));

        mockMvc.perform(post("/course/information/create/" + COURSE_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("courseId"))
                .andExpect(model().attributeExists("courseInformation"))
                .andExpect(view().name("course/createCourseInformation"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantCreateCourseInformationPostWhenItsInvalid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(true);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseService.getCourseByTeacherAndStatus(teacher, false)).thenReturn(course);

        mockMvc.perform(post("/course/information/create/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());


        course.setArchived((false));
        var teacher1 = new Teacher();
        course.setTeacher(teacher1);
        mockMvc.perform(post("/course/information/create/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());

        teacher.setHaveCourse(false);
        mockMvc.perform(post("/course/information/create/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessCreateUpdateInformationGet() throws Exception {
        mockMvc.perform(get("/course/information/update/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));;
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCanAccessCreateUpdateInformationGetWhenItsValid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseInformationService.getCourseInformationById(COURSE_INFORMATION_ID)).thenReturn(courseInformation);

        mockMvc.perform(get("/course/information/update/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("courseId"))
                .andExpect(model().attributeExists("courseInformation"))
                .andExpect(model().attributeExists("courseInformationId"))
                .andExpect(view().name("course/updateCourseInformation"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantAccessUpdateCourseInformationGetWhenItsInvalid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(true);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseService.getCourseByTeacherAndStatus(teacher, false)).thenReturn(course);

        mockMvc.perform(get("/course/information/update/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection());


        course.setArchived((false));
        var teacher1 = new Teacher();
        course.setTeacher(teacher1);
        mockMvc.perform(get("/course/information/update/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection());

        teacher.setHaveCourse(false);
        mockMvc.perform(get("/course/information/update/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessUpdateCourseInformationPost() throws Exception {
        mockMvc.perform(post("/course/information/update/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCanUpdateCourseInformationPostWhenItsValid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseInformationService.updateCourseInformation(COURSE_INFORMATION_ID, courseInformation)).thenReturn(courseInformation);

        mockMvc.perform(post("/course/information/update/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantUpdateCourseInformationPostWhenItHasInvalidAttr() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseInformationService.updateCourseInformation(anyInt(), any(CourseInformation.class))).thenThrow(new CourseInformationInvalidException("Required Title"));

        mockMvc.perform(post("/course/information/update/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("courseId"))
                .andExpect(model().attributeExists("courseInformation"))
                .andExpect(model().attributeExists("courseInformationId"))
                .andExpect(view().name("course/updateCourseInformation"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantUpdateCourseInformationPostWhenItsInvalid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(true);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseService.getCourseByTeacherAndStatus(teacher, false)).thenReturn(course);

        mockMvc.perform(post("/course/information/update/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection());


        course.setArchived((false));
        var teacher1 = new Teacher();
        course.setTeacher(teacher1);
        mockMvc.perform(post("/course/information/update/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection());

        teacher.setHaveCourse(false);
        mockMvc.perform(post("/course/information/update/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantDeleteCourseInformationPost() throws Exception {
        mockMvc.perform(post("/course/information/delete/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCanDeleteCourseInformationPostWhenItsValid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseInformationService.updateCourseInformation(COURSE_INFORMATION_ID, courseInformation)).thenReturn(courseInformation);

        mockMvc.perform(post("/course/information/delete/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/course/" + COURSE_ID));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantDeleteCourseInformationPostWhenItsInvalid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(true);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseService.getCourseByTeacherAndStatus(teacher, false)).thenReturn(course);

        mockMvc.perform(post("/course/information/delete/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection());


        course.setArchived((false));
        var teacher1 = new Teacher();
        course.setTeacher(teacher1);
        mockMvc.perform(post("/course/information/delete/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection());

        teacher.setHaveCourse(false);
        mockMvc.perform(post("/course/information/delete/" + COURSE_ID + "/" + COURSE_INFORMATION_ID))
                .andExpect(status().is3xxRedirection());
    }

}
