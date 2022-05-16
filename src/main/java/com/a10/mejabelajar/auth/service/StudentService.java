package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();

    Student getStudentById(String id);

    Student getStudentByUserId(String id);
}
