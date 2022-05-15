package com.a10.mejabelajar.admin.service;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.admin.model.LogStatus;
import com.a10.mejabelajar.admin.repository.LogRepository;
import com.a10.mejabelajar.admin.util.AdminStrategy;
import com.a10.mejabelajar.admin.util.Strategy;
import com.a10.mejabelajar.admin.util.StudentStrategy;
import com.a10.mejabelajar.admin.util.TeacherStrategy;
import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.auth.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService{
    @Autowired
    LogRepository logRepository;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;


    @Override
    public List<Log> getLogs(User user) {
        Strategy strategy;
        List<Log> logs = getAll();
        switch (user.getRole()) {
            case ADMIN:
                strategy = new AdminStrategy();
                break;
            case TEACHER:
                Teacher teacher = teacherService.getTeacherByUser(user);
                strategy = new TeacherStrategy(teacher);
                break;
            case STUDENT:
                Student student = studentService.getStudentByUser(user);
                strategy = new StudentStrategy(student);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + user.getRole());
        }
        return strategy.getLogs(logs);
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
                .logStatus(LogStatus.BELUM_BAYAR)
                .build();
        return logRepository.save(log);
    }

    @Override
    public List<Log> getAll() {
        return logRepository.findAll();
    }

    @Override
    public Log getLogById(String logId) {
        return logRepository.findById(logId).get();
    }

    @Override
    public Log bayarLog(Log log) {
        log.setLogStatus(LogStatus.VERIFIKASI);
        logRepository.save(log);
        return log;
    }

    @Override
    public Log verifikasiLog(Log log) {
        log.setLogStatus(LogStatus.LUNAS);
        logRepository.save(log);
        return log;
    }

}
