package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.repository.CourseInformationRepository;
import com.a10.mejabelajar.course.validator.CourseInformationValidator;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CourseInformationServiceImpl implements CourseInformationService {

    @Autowired
    CourseInformationRepository courseInformationRepository;

    @Override
    public List<CourseInformation> getCourseInformationByCourse(Course course) {
        return courseInformationRepository.findByCourse(course);
    }

    @Override
    public CourseInformation getCourseInformationById(int id) {
        return courseInformationRepository.findById(id);
    }

    @Override
    public CourseInformation createCourseInformation(CourseInformation courseInformation) {
        CourseInformationValidator.validateCourseInformation(courseInformation);
        courseInformation.setUpdatedAt(new Date());
        courseInformationRepository.save(courseInformation);
        return courseInformation;
    }

    @Override
    public CourseInformation updateCourseInformation(int id, CourseInformation courseInformation) {
        CourseInformationValidator.validateCourseInformation(courseInformation);
        var oldCourseInformation = getCourseInformationById(id);
        courseInformation.setId(id);
        courseInformation.setCreatedAt(oldCourseInformation.getCreatedAt());
        courseInformation.setUpdatedAt(new Date());
        courseInformation.setCourse(oldCourseInformation.getCourse());
        courseInformationRepository.save(courseInformation);
        return  courseInformation;
    }

    @Override
    public void deleteCourseInformationById(int id) {
        var courseInformation = getCourseInformationById(id);
        courseInformationRepository.delete(courseInformation);
    }
}
