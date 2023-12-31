package com.a10.mejabelajar.murid.service;

import com.a10.mejabelajar.auth.repository.StudentRepository;
import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.murid.model.Rate;
import com.a10.mejabelajar.murid.repository.RateRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Rate createRate(String id, Integer courseId, Integer rate) {
        var newRate = Rate.builder().idStudent(id).idCourse(courseId).nilaiRating(rate).build();
        return rateRepository.save(newRate);
    }

    @Override
    public List<Rate> getListRate() {
        return rateRepository.findAll();
    }

    @Override
    public Rate getByIdStudentAndIdCourse(String idStudent, int idCourse) {
        return rateRepository.findByIdStudentAndIdCourse(idStudent, idCourse);
    }

    @Override
    public Double getCourseAverageRateByIdCourse(int idCourse) {
        return rateRepository.findAverageRateByIdCourse(idCourse);
    }
}