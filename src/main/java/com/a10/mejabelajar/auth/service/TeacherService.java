package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import java.util.List;

public interface TeacherService {
    List<Teacher> getTeachers();

    Teacher getTeacherByUser(User user);
}
