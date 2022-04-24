package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import java.util.List;

public interface CourseInformationService {
    List<CourseInformation> getCourseInformationByCourse(Course course);

    CourseInformation getCourseInformationById(int id);

    CourseInformation createCourseInformation(CourseInformation courseInformation);

    CourseInformation updateCourseInformation(int id, CourseInformation courseInformation);

    void deleteCourseInformationById(int id);
}
