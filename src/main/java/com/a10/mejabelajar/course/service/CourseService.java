package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import java.util.List;

public interface CourseService {
    Course createCourse(CourseDataTransferObject courseDataTransferObject, User user);

    Course createCourse(Course course);

    List<Course> getCourses();

    Course getCourseById(int id);

    Course getCourseByTeacherAndStatus(Teacher teacher, boolean status);

    Course updateCourse(int id, Teacher teacher, CourseDataTransferObject courseDataTransferObject);

    void archiveCourseById(User user, int id);
}
