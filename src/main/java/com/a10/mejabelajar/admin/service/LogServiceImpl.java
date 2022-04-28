package com.a10.mejabelajar.admin.service;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.admin.repository.LogRepository;
import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository logRepository;

    @Override
    public List<Log> getLogs() {
        return logRepository.findAll();
    }

    @Override
    public void deleteLog(String id) {
        logRepository.deleteById(id);
    }

    @Override
    public Log createLog(double hour, String desc, Student student, Teacher teacher) {
        var log = Log.builder()
                .hour(hour)
                .desc(desc)
                .student(student)
                .teacher(teacher)
                .build();
        return logRepository.save(log);
    }
}
