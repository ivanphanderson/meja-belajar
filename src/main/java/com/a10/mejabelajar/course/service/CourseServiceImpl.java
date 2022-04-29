package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.course.exception.CourseInvalidException;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseType;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import com.a10.mejabelajar.course.repository.CourseRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherService teacherService;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Course createCourse(Course course) {
        courseRepository.save(course);
        return course;
    }

    @Override
    public Course createCourse(CourseDataTransferObject courseDataTransferObject, User user) {
        validateCourseAttribute(courseDataTransferObject);
        var course = new Course();
        modelMapper.map(courseDataTransferObject, course);
        var courseType1 =  CourseType.valueOf(courseDataTransferObject.getCourseType());
        var durationInt = Double.parseDouble(courseDataTransferObject.getCourseDuration());
        course.setCourseType(courseType1);
        course.setCourseDuration(durationInt);

        var teacher = teacherService.getTeacherByUser(user);
        teacher.setCourse(course);
        courseRepository.save(course);
        return course;
    }

    @Override
    public Course updateCourse(int id, CourseDataTransferObject courseDataTransferObject) {
        validateCourseAttribute(courseDataTransferObject);

        var course = new Course();
        modelMapper.map(courseDataTransferObject, course);
        var courseType1 =  CourseType.valueOf(courseDataTransferObject.getCourseType());
        var durationInt = Double.parseDouble(courseDataTransferObject.getCourseDuration());
        course.setCourseType(courseType1);
        course.setCourseDuration(durationInt);
        course.setId(id);
        courseRepository.save(course);
        return course;
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
    public void deleteCourseById(User user, int id) {
        var course = getCourseById(id);
        var teacher = teacherService.getTeacherByUser(user);
        teacher.setCourse(null);
        courseRepository.delete(course);
    }

    private void validateCourseAttribute(CourseDataTransferObject courseDataTransferObject) {
        if (!validateCourseName(courseDataTransferObject.getCourseName())) {
            throw new CourseInvalidException("Course name should not be empty");
        }
        if (!validateCourseType(courseDataTransferObject.getCourseType())) {
            throw new CourseInvalidException("Choose valid course type!");
        }
        if (!validateCourseDuration(courseDataTransferObject.getCourseDuration())) {
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
}
