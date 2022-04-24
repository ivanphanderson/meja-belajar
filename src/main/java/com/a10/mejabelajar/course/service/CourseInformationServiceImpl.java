package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.course.exception.CourseInformationInvalidException;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.repository.CourseInformationRepository;
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
        validateCourseInformation(courseInformation);
        courseInformation.setUpdatedAt(new Date());
        courseInformationRepository.save(courseInformation);
        return courseInformation;
    }

    @Override
    public CourseInformation updateCourseInformation(int id, CourseInformation courseInformation) {
        validateCourseInformation(courseInformation);
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

    private void validateCourseInformation(CourseInformation courseInformation) {
        if (courseInformation.getCourseInformationTitle().equals("")) {
            throw new CourseInformationInvalidException("Required Title");
        }
    }
}
