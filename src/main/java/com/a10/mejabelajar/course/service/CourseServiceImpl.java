package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.course.exception.CourseInvalidException;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseType;
import com.a10.mejabelajar.course.repository.CourseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course createCourse(String courseName,
                               String courseType,
                               String courseDescription,
                               String courseDuration) {
        validateCourseAttribute(courseName, courseType, courseDuration);
        var courseType1 =  CourseType.valueOf(courseType);
        var durationInt = Double.parseDouble(courseDuration);
        var course = new Course(courseName, courseType1, courseDescription, durationInt);
        courseRepository.save(course);
        return course;
    }

    @Override
    public Course updateCourse(int id,
                               String courseName,
                               String courseType,
                               String courseDescription,
                               String courseDuration) {
        validateCourseAttribute(courseName, courseType, courseDuration);
        var courseType1 =  CourseType.valueOf(courseType);
        var durationInt = Double.parseDouble(courseDuration);
        var course = new Course(courseName, courseType1, courseDescription, durationInt);
        course.setId(id);
        courseRepository.save(course);
        return course;
    }

    private void validateCourseAttribute(String courseName,
                                         String courseType,
                                         String courseDuration) {
        if (!validateCourseName(courseName)) {
            throw new CourseInvalidException("Course name should not be empty");
        }
        if (!validateCourseType(courseType)) {
            throw new CourseInvalidException("Choose valid course type!");
        }
        if (!validateCourseDuration(courseDuration)) {
            throw new CourseInvalidException("Duration should be a positive Integer");
        }
    }

    private boolean validateCourseName(String courseName) {
        return (!courseName.equals(""));
    }

    private boolean validateCourseType(String userCourseType) {
        try {
            CourseType.valueOf(userCourseType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean validateCourseDuration(String duration) {
        try {
            var durationDouble = Double.parseDouble(duration);
            return durationDouble > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(int id) {
        return courseRepository.findById(id);
    }

    @Override
    public void deleteCourseById(int id) {
        var course = getCourseById(id);
        courseRepository.delete(course);
    }
}
