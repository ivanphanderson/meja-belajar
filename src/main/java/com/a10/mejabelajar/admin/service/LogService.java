package com.a10.mejabelajar.admin.service;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;

import java.util.List;

public interface LogService {
    List<Log> getLogs();

    void deleteLog(String id);

    Log createLog(double hour, String desc, Student student, Teacher teacher);
}
