package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.StudentRepository;
import com.a10.mejabelajar.course.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    private Student student;
    private Course course;
    private User user;
    private Date date;
    private static final String STUDENT_ID = "0e403c72-abcd-abcd-abcd-a1450c032b8a";

    @BeforeEach
    void setUp() {
        user = new User();
        course = new Course();
        student = new Student();
        date = new Date();

        student.setId(STUDENT_ID);
        student.setUser(user);
        student.setLastNotifBtnClick(date);
    }

    @Test
    void testGetAllStudents() {
        assertNotNull(studentService.getStudents());
    }

    @Test
    void testGetStudentById() {
        when(studentService.getStudentById(STUDENT_ID)).thenReturn(student);
        var nStudent = studentService.getStudentById(STUDENT_ID);
        assertEquals(STUDENT_ID, nStudent.getId());
    }

    @Test
    void testGetStudentByUser() {
        when(studentService.getStudentByUser(user)).thenReturn(student);
        var nStudent = studentService.getStudentByUser(user);
        assertEquals(STUDENT_ID, nStudent.getId());
    }

    @Test
    void testGetStudentsByCourse() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentService.getStudentsByCourse(course)).thenReturn(students);
        assertNotNull(studentService.getStudentsByCourse(course));
    }

    @Test
    void testSetStudentLastNotifBtnClick() {
        Date ndate = new Date();
        studentService.setStudentLastNotifBtnClick(student, ndate);
        assertEquals(student.getLastNotifBtnClick(), ndate);
    }
}
