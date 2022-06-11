package com.a10.mejabelajar.dashboard.student.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.course.model.Course;

import java.util.List;

public interface StudentDashboardService {
    List<Course> getTakenCourse(Student student);
}
