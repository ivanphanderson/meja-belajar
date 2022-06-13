package com.a10.mejabelajar.admin.service;

import com.a10.mejabelajar.admin.exception.LogInvalidException;
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
import org.apache.tomcat.util.net.SendfileDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
    public Log createLog(String starts, String ends, String duration, String desc, Student student, Teacher teacher) {
        LocalDateTime start = convertStringtoLocalDatiTime(starts);
        LocalDateTime end = convertStringtoLocalDatiTime(ends);
        var log = Log.builder()
                .start(start)
                .end(end)
                .duration(duration)
                .desc(desc)
                .student(student)
                .teacher(teacher)
                .logStatus(LogStatus.BELUM_BAYAR)
                .build();
        return logRepository.save(log);
    }

    @Override
    public String countDuration(String starts, String ends) {
        LocalDateTime start = convertStringtoLocalDatiTime(starts);
        LocalDateTime end = convertStringtoLocalDatiTime(ends);
        long durationInMinutes = Duration.between(start, end).toMinutes();
        validateLogTime(durationInMinutes);
        long hour = durationInMinutes / 60;
        long minute = durationInMinutes % 60;
        if(minute == 0) return hour + " jam";
        if(hour == 0) return minute + "menit";
        return hour + " jam " + minute + " menit";
    }

    @Override
    public List<Log> getAll() {
        return logRepository.findAll();
    }

    @Override
    public Log getLogById(String logId) {
        Optional<Log> log = logRepository.findById(logId);
        return log.orElse(null);
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

    private void validateLogTime(long duration) {
        if(tooLong(duration)) {
            throw new LogInvalidException("Durasi tidak bisa lebih dari 8 jam");
        }
        if(endBeforeStart(duration)) {
            throw new LogInvalidException("Waktu selesai harus setelah waktu mulai");
        }
    }

    private boolean tooLong(long duration) {
        return duration > 8*60;
    }

    private boolean endBeforeStart(long duration) {
        return duration < 0;
    }

    private LocalDateTime convertStringtoLocalDatiTime(String times) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(times, formatter);
    }

}
