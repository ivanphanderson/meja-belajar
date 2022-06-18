package com.a10.mejabelajar.murid.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.repository.StudentRepository;
import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.murid.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MuridServiceImpl implements MuridService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RateRepository rateRepository;

    /**
     * Tambahin murid ke course.
     */
    @Override
    public Student updateMurid(int id, Student student) {
        var newCourse = courseRepository.findById(id);
        student.getNewCourse().add(newCourse);
        newCourse.getNewMurid().add(student);
        studentRepository.save(student);
        return student;
    }
}
