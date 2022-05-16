package com.a10.mejabelajar.murid.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.repository.StudentRepository;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.murid.model.Murid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MuridServiceImpl implements MuridService{

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Student regisMurid(Student student) {
        studentRepository.save(student);
        return student;
    }

    /**
     * Tambahin murid ke course.
     */
    @Override
    public Student updateMurid(int id, Student student) {
        //Student newMurid = studentRepository.findById(id);
        //student.setIdCourse(id);
        student.setCourse(courseRepository.findById(id));
        Course newCourse = courseRepository.findById(id);
        //newCourse.set
        studentRepository.save(student);
        return student;
    }

//    public Murid getMuridById(int id) {
//        return studentRepository.findById(id);
//    }
}
