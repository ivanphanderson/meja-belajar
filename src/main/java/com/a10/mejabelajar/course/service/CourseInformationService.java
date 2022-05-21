package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.course.model.CourseInformation;

public interface CourseInformationService {
    CourseInformation getCourseInformationById(int id);

    CourseInformation createCourseInformation(CourseInformation courseInformation);

    CourseInformation updateCourseInformation(int id, CourseInformation courseInformation);

    void deleteCourseInformationById(int id);
}
