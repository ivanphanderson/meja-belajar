package com.a10.mejabelajar.murid.service;
import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.repository.StudentRepository;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.murid.repository.RateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MuridServiceImplTest {

    Student student;
    Course course;


    StudentRepository studentRepository;
    CourseRepository courseRepository;
    RateRepository rateRepository;
    @InjectMocks
    MuridServiceImpl muridService;

    @BeforeEach
    void setUp(){
        studentRepository = Mockito.mock(StudentRepository.class);
        courseRepository = Mockito.mock(CourseRepository.class);
        rateRepository = Mockito.mock(RateRepository.class);
        student = Mockito.mock(Student.class);
        course = Mockito.mock(Course.class);
        MockitoAnnotations.openMocks(this);
        when(courseRepository.findById(1)).thenReturn(course);
    }
    @Test
    void registMuridTest(){
        var returnStudent =  muridService.regisMurid(student);
        assertEquals(student, returnStudent);
    }

    @Test
    void updateMuridTest(){
        var returnStudent = muridService.updateMurid(1, student);
        assertEquals(student, returnStudent);
    }
}
