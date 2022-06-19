package com.a10.mejabelajar.dashboard.student.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDashboardServiceImpl implements StudentDashboardService{
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> getTakenCourse(Student student) {
        return courseRepository.findAllByNewMurid(student);
    }
}
