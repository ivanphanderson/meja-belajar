package com.a10.mejabelajar.admin.util.Behaviour;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.admin.repository.LogRepository;
import com.a10.mejabelajar.admin.service.LogService;
import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GetLogsStudent implements GetLogsBehaviour{
    Student student;
    public GetLogsStudent(Student student) {
        this.student = student;
    }

    @Override
    public List<Log> get(List<Log> logs) {
        return student.getLog();
    }
}
