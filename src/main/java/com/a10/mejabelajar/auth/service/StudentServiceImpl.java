package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.StudentRepository;
import com.a10.mejabelajar.course.model.Course;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(String id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student getStudentByUser(User user) {
        return studentRepository.findByUser(user);
    }

    @Override
    public List<Student> getStudentsByCourse(Course course) {
        return studentRepository.findAllByNewCourse(course);
    }

    @Override
    public Student setStudentLastNotifBtnClick(Student student, Date date) {
        student.setLastNotifBtnClick(date);
        studentRepository.save(student);
        return null;
    }
}
