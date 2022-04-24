package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import java.util.List;

public interface CourseService {
    Course createCourse(CourseDataTransferObject courseDataTransferObject);

    List<Course> getCourses();

    Course getCourseById(int id);

    Course updateCourse(int id, CourseDataTransferObject courseDataTransferObject);

    void deleteCourseById(int id);
}
