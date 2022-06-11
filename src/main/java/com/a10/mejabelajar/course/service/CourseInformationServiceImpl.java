package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.repository.CourseInformationRepository;
import com.a10.mejabelajar.course.validator.CourseInformationValidator;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CourseInformationServiceImpl implements CourseInformationService {

    public static final long HOUR = 3600L * 1000; // in milli-seconds.

    @Autowired
    CourseInformationRepository courseInformationRepository;

    @Override
    public CourseInformation getCourseInformationById(int id) {
        return courseInformationRepository.findById(id);
    }

    @Override
    public CourseInformation createCourseInformation(CourseInformation courseInformation) {
        CourseInformationValidator.validateCourseInformation(courseInformation);
        var instant = Instant.now();
        var date = Date.from(instant);
        var newDate = new Date(date.getTime() + 7 * HOUR);
        courseInformation.setCreatedAt(newDate);
        courseInformation.setUpdatedAt(newDate);
        return courseInformationRepository.save(courseInformation);
    }

    @Override
    public CourseInformation updateCourseInformation(int id, CourseInformation courseInformation) {
        CourseInformationValidator.validateCourseInformation(courseInformation);
        var oldCourseInformation = getCourseInformationById(id);
        courseInformation.setId(id);
        courseInformation.setCreatedAt(oldCourseInformation.getCreatedAt());

        var instant = Instant.now();
        var date = Date.from(instant);
        var newDate = new Date(date.getTime() + 7 * HOUR);
        courseInformation.setUpdatedAt(newDate);
        courseInformation.setCourse(oldCourseInformation.getCourse());
        return courseInformationRepository.save(courseInformation);
    }

    @Override
    public void deleteCourseInformationById(int id) {
        var courseInformation = getCourseInformationById(id);
        courseInformation.setCourse(null);
        courseInformationRepository.save(courseInformation);
    }
}
