package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.TeacherRepository;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseType;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.course.validator.CourseValidator;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherService teacherService;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Course createCourse(CourseDataTransferObject courseDataTransferObject, User user) {
        CourseValidator.validateCourseAttribute(courseDataTransferObject);
        var course = new Course();
        modelMapper.map(courseDataTransferObject, course);
        var courseType1 =  CourseType.valueOf(courseDataTransferObject.getCourseType());
        var durationInt = Double.parseDouble(courseDataTransferObject.getCourseDuration());
        course.setCourseType(courseType1);
        course.setCourseDuration(durationInt);

        var teacher = teacherService.getTeacherByUser(user);
        teacher.setHaveCourse(true);
        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(
            int id,
            Teacher teacher,
            CourseDataTransferObject courseDataTransferObject) {

        CourseValidator.validateCourseAttribute(courseDataTransferObject);

        var course = new Course();
        modelMapper.map(courseDataTransferObject, course);
        var courseType1 =  CourseType.valueOf(courseDataTransferObject.getCourseType());
        var durationInt = Double.parseDouble(courseDataTransferObject.getCourseDuration());
        course.setCourseType(courseType1);
        course.setCourseDuration(durationInt);
        course.setId(id);
        course.setTeacher(teacher);
        course.setArchived(false);
        return courseRepository.save(course);
    }

    @Override
    public void archiveCourseById(Teacher teacher, Course course) {
        teacher.setHaveCourse(false);
        course.setArchived(true);
        courseRepository.save(course);
        teacherRepository.save(teacher);
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
    public List<Course> getCoursesByStudent(Student student) {
        return courseRepository.findAllByNewMurid(student);
    }

    @Override
    public Course getCourseByTeacherAndStatus(Teacher teacher, boolean status) {
        return courseRepository.findByTeacherAndArchived(teacher, status);
    }

    @Override
    public List<Course> getCourseByArchived(boolean archived) {
        return courseRepository.findAllByArchived(archived);
    }
}
