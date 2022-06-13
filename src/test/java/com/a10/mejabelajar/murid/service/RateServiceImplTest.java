package com.a10.mejabelajar.murid.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.repository.StudentRepository;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.murid.model.Rate;
import com.a10.mejabelajar.murid.repository.RateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RateServiceImplTest {

    Student student;
    Course course;
    ArrayList<Rate> rateList;
    Rate rate;
    StudentRepository studentRepository;
    CourseRepository courseRepository;
    RateRepository rateRepository;
    @InjectMocks
    RateServiceImpl rateService;

    @BeforeEach
    void setUp(){
        studentRepository = Mockito.mock(StudentRepository.class);
        courseRepository = Mockito.mock(CourseRepository.class);
        rateRepository = Mockito.mock(RateRepository.class);
        student = Mockito.mock(Student.class);
        course = Mockito.mock(Course.class);
        MockitoAnnotations.openMocks(this);
        rate = new Rate(1,2, 5, "Student");
        rateList = new ArrayList<>();
        when(rateRepository.save(any(Rate.class))).thenReturn(rate);
        when(rateRepository.findAll()).thenReturn(rateList);
        when(rateRepository.findByIdStudentAndIdCourse("Student", 1)).thenReturn(rate);
        when(rateRepository.findAverageRateByIdCourse(1)).thenReturn(5.0);
    }

    @Test
    void createRateTest(){
        var newRate = rateService.createRate("Student", 1, 5);
        assertEquals(rate, newRate);
    }

    @Test
    void getListRateTest() {
        var newListRate = rateService.getListRate();
        assertEquals(rateList, newListRate);
    }

    @Test
    void getByIdStudentAndIdCourseTest(){
        var newRate = rateService.getByIdStudentAndIdCourse("Student", 1);
        assertEquals(rate, newRate);
    }
    @Test
    void getCourseAverageRateByIdCourseTest(){
        var newAvarageRating = rateService.getCourseAverageRateByIdCourse(1);
        assertEquals(5.0, newAvarageRating);
    }
}
