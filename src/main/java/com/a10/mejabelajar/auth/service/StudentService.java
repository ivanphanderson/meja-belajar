package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.User;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();

    Student getStudentById(String id);

    Student getStudentByUser(User user);
}
