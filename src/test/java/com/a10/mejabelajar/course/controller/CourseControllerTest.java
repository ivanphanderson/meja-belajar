package com.a10.mejabelajar.course.controller;

import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.auth.service.UserService;
import com.a10.mejabelajar.course.exception.CourseInvalidException;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private RateService rateService;

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
    }


    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessCreateCourseGet() throws Exception {
        mockMvc.perform(get("/course/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));;
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCanAccessCreateCourseGetWhenDontHaveActiveCourse() throws Exception {
        teacher.setHaveCourse(false);
        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        mockMvc.perform(get("/course/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("courseTypes"))
                .andExpect(model().attributeExists("newCourse"))
                .andExpect(view().name("course/createCourse"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantAccessCreateCourseGetWhenHaveActiveCourse() throws Exception {
        teacher.setHaveCourse(true);
        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseByTeacherAndStatus(teacher, false)).thenReturn(course);
        mockMvc.perform(get("/course/create"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessCreateCoursePost() throws Exception {
        mockMvc.perform(post("/course/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));;
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCanCreateCoursePostWhenDontHaveActiveCourse() throws Exception {
        CourseDataTransferObject courseDataTransferObject = new CourseDataTransferObject();
        courseDataTransferObject.setCourseName("course");
        courseDataTransferObject.setCourseType("IPA");
        courseDataTransferObject.setCourseDescription("Auto lulus UTBK");
        courseDataTransferObject.setCourseDuration("100");

        teacher.setHaveCourse(false);
        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.createCourse(courseDataTransferObject, user)).thenReturn(course);

        mockMvc.perform(post("/course/create"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantCreateCoursePostWhenGivingInvalidAttr() throws Exception {
        CourseDataTransferObject courseDataTransferObject = new CourseDataTransferObject();
        courseDataTransferObject.setCourseName("");
        courseDataTransferObject.setCourseType("IPA");
        courseDataTransferObject.setCourseDescription("Auto lulus UTBK");
        courseDataTransferObject.setCourseDuration("100");

        teacher.setHaveCourse(false);
        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.createCourse(any(CourseDataTransferObject.class), any(User.class))).thenThrow(new CourseInvalidException("Course name should not be empty"));

        mockMvc.perform(post("/course/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("courseTypes"))
                .andExpect(model().attributeExists("newCourse"))
                .andExpect(view().name("course/createCourse"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantAccessCreateCoursePostWhenHaveActiveCourse() throws Exception {
        teacher.setHaveCourse(true);
        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseByTeacherAndStatus(teacher, false)).thenReturn(course);
        mockMvc.perform(post("/course/create"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessUpdateCourseGet() throws Exception {
        mockMvc.perform(get("/course/update/" + COURSE_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));;
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCanAccessUpdateCourseGet() throws Exception {
        teacher.setHaveCourse(false);
        course.setArchived(false);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);

        mockMvc.perform(get("/course/update/" + COURSE_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("courseTypes"))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attributeExists("courseId"))
                .andExpect(view().name("course/updateCourse"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantAccessUpdateCourseGetWhenItsInvalid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);

        Teacher teacher1 = new Teacher();
        course.setTeacher(teacher1);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);

        mockMvc.perform(get("/course/update/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());

        course.setArchived(true);
        mockMvc.perform(post("/course/update/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessUpdateCoursePost() throws Exception {
        mockMvc.perform(post("/course/update/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCanUpdateCoursePostWhenItsValid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);


        CourseDataTransferObject courseDataTransferObject = new CourseDataTransferObject();
        courseDataTransferObject.setCourseName("course update");
        courseDataTransferObject.setCourseType("IPA");
        courseDataTransferObject.setCourseDescription("Auto lulus UTBK");
        courseDataTransferObject.setCourseDuration("100");


        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseService.updateCourse(COURSE_ID, teacher, courseDataTransferObject)).thenReturn(course);

        mockMvc.perform(post("/course/update/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantUpdateCoursePostWhenGivingInvalidAttr() throws Exception {

        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseService.updateCourse(anyInt(), any(Teacher.class), any(CourseDataTransferObject.class))).thenThrow(new CourseInvalidException("Duration should be a positive Integer"));

        mockMvc.perform(post("/course/update/" + COURSE_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("courseTypes"))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attributeExists("courseId"))
                .andExpect(view().name("course/updateCourse"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantAccessUpdateCoursePostWhenItsInvalid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);

        Teacher teacher1 = new Teacher();
        course.setTeacher(teacher1);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);

        mockMvc.perform(post("/course/update/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());

        course.setArchived(true);
        course.setTeacher(teacher);
        mockMvc.perform(post("/course/update/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessReadCourseGet() throws Exception {
        mockMvc.perform(get("/course/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));;
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessReadCourseByIdGet() throws Exception {
        mockMvc.perform(get("/course/" + COURSE_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));;
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCanAccessReadCourseByIdGetWhenValid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);
        user.setRole(Role.TEACHER);

        List<CourseInformation> courseInformationList = new ArrayList<>();
        courseInformationList.add(courseInformation);
        course.setCourseInformations(courseInformationList);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);

        mockMvc.perform(get("/course/" + COURSE_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("teacher"))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attributeExists("courseInformations"))
                .andExpect(view().name("course/readCourseById"));
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantAccessReadCourseByIdGetWhenInvalid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        user.setRole(Role.TEACHER);

        var teacher1 = new Teacher();
        course.setTeacher(teacher1);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);

        mockMvc.perform(get("/course/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = STUDENT_UNAME, authorities = {"USER", "STUDENT"})
    public void studentCanAccessReadCourseByIdGetWhenValid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);
        user.setRole(Role.STUDENT);

        List<CourseInformation> courseInformationList = new ArrayList<>();
        courseInformationList.add(courseInformation);
        course.setCourseInformations(courseInformationList);

        var course1 = new Course();
        List<Course> courses = new ArrayList<>();
        courses.add(course); courses.add(course1);

        when(userService.getUserByUsername(STUDENT_UNAME)).thenReturn(user);
        when(studentService.getStudentByUser(user)).thenReturn(student);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseService.getCoursesByStudent(student)).thenReturn(courses);

        mockMvc.perform(get("/course/" + COURSE_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attributeExists("courseInformations"))
                .andExpect(view().name("course/readCourseById"));
    }

    @Test
    @WithMockUser(username = STUDENT_UNAME, authorities = {"USER", "STUDENT"})
    public void studentCantAccessReadCourseByIdGetWhenInvalid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);
        user.setRole(Role.STUDENT);

        List<CourseInformation> courseInformationList = new ArrayList<>();
        courseInformationList.add(courseInformation);
        course.setCourseInformations(courseInformationList);

        var course1 = new Course();
        List<Course> courses = new ArrayList<>();
        courses.add(course1);

        when(userService.getUserByUsername(STUDENT_UNAME)).thenReturn(user);
        when(studentService.getStudentByUser(user)).thenReturn(student);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);
        when(courseService.getCoursesByStudent(student)).thenReturn(courses);

        mockMvc.perform(get("/course/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void notLoggedInCantAccessArchiveCoursePost() throws Exception {
        mockMvc.perform(post("/course/archive/" + COURSE_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));;
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCanArchiveCoursePostWhenItsValid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);
        course.setTeacher(teacher);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);

        mockMvc.perform(post("/course/archive/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = TEACHER_UNAME, authorities = {"USER", "TEACHER"})
    public void teacherCantAccessArchiveCoursePostWhenItsInvalid() throws Exception {
        teacher.setHaveCourse(true);
        course.setArchived(false);

        Teacher teacher1 = new Teacher();
        course.setTeacher(teacher1);

        when(userService.getUserByUsername(TEACHER_UNAME)).thenReturn(user);
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        when(courseService.getCourseById(COURSE_ID)).thenReturn(course);

        mockMvc.perform(post("/course/archive/" + COURSE_ID))
                .andExpect(status().is3xxRedirection());
    }

}

