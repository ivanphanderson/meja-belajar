package com.a10.mejabelajar.murid.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.repository.StudentRepository;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.course.service.CourseService;
import com.a10.mejabelajar.murid.model.Murid;
import com.a10.mejabelajar.murid.model.Rate;
import com.a10.mejabelajar.murid.repository.MuridRepository;
import com.a10.mejabelajar.murid.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService{

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    MuridRepository muridRepository;

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student createRate(String id, Rate rate) {
        Course newId = courseRepository.findById(rate.getIdCourse());
        Student newId2 = studentRepository.findById(id);
        rate.setCourse(newId);
        rate.setStudent(newId2);
        rateRepository.save(rate);
        return newId2;
    }
}