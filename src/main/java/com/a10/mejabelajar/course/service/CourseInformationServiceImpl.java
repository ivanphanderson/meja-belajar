package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.repository.CourseInformationRepository;
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

    public CourseInformation createCourseInformation(CourseInformation courseInformation) {
        courseInformationRepository.save(courseInformation);
        return courseInformation;
    }
}
