package com.a10.mejabelajar.dashboard.teacher.service;

import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.repository.CourseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherDashboardServiceImpl implements TeacherDashboardService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course getActiveCourse(Teacher teacher) {
        return courseRepository.findByTeacherAndArchived(teacher, false);
    }

    @Override
    public List<Course> getArchivedCourse(Teacher teacher) {
        return courseRepository.findAllByTeacherAndArchived(teacher, true);
    }
}
