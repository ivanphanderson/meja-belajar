package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import java.util.List;

public interface CourseService {
    Course createCourse(CourseDataTransferObject courseDataTransferObject, User user);

    List<Course> getCourses();

    Course getCourseById(int id);

    Course getCourseByTeacherAndStatus(Teacher teacher, boolean status);

    List<Course> getCoursesByStudent(Student student);

    Course updateCourse(int id, Teacher teacher, CourseDataTransferObject courseDataTransferObject);

    void archiveCourseById(Teacher teacher, Course course);

    List<Course> getCourseByArchived(boolean archived);
}
