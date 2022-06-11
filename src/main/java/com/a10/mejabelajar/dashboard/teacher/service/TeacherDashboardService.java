package com.a10.mejabelajar.dashboard.teacher.service;

import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.course.model.Course;
import java.util.List;

public interface TeacherDashboardService {
    Course getActiveCourse(Teacher teacher);

    List<Course> getArchivedCourse(Teacher teacher);
}
