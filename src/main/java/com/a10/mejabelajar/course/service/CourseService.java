package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.course.model.Course;
import java.util.List;

public interface CourseService {
    Course createCourse(String courseName, String courseType, String courseDescription, String courseDuration);

    List<Course> getCourses();

    Course getCourseById(int id);

    Course updateCourse(int id, String courseName, String courseType, String courseDescription, String courseDuration);

    void deleteCourseById(int id);
}
