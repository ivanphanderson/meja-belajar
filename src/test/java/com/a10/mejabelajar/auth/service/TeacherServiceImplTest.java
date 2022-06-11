package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.StudentRepository;
import com.a10.mejabelajar.auth.repository.TeacherRepository;
import com.a10.mejabelajar.course.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {
    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    private User user;
    private Teacher teacher;
    private static final String Teacher_ID = "0e403c72-abcd-abcd-abcd-a1450c032b8a";

    @BeforeEach
    void setUp() {
        user = new User();
        teacher = new Teacher();

        teacher.setId(Teacher_ID);
    }

    @Test
    void testGetAllTeachers() {
        assertNotNull(teacherService.getTeachers());
    }

    @Test
    void testGetTeacherByUser() {
        when(teacherService.getTeacherByUser(user)).thenReturn(teacher);
        var nTacher = teacherService.getTeacherByUser(user);
        assertEquals(Teacher_ID, nTacher.getId());
    }
}
