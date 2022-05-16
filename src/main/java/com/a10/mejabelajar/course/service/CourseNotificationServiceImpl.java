package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.course.model.CourseInformation;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseNotificationServiceImpl implements CourseNotificationService {

    @Autowired
    StudentService studentService;

    @Override
    public void handleCreateInformation(CourseInformation courseInformation) {

    }

    @Override
    public void handleUpdateInformation(CourseInformation courseInformation) {

    }
}
