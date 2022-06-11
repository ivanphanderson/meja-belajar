package com.a10.mejabelajar.admin.service;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import java.util.List;

public interface LogService {
    List<Log> getLogs(User user);

    void deleteLog(String id);

    Log createLog(String starts, String ends, String duration, String desc, Student student, Teacher teacher);
    String countDuration(String start, String end);

    List<Log> getAll();

    Log getLogById(String logId);

    Log bayarLog(Log log);
    Log verifikasiLog(Log log);
}
