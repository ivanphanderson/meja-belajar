package com.a10.mejabelajar.admin.util.Behaviour;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.admin.repository.LogRepository;
import com.a10.mejabelajar.admin.service.LogService;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.TeacherRepository;
import com.a10.mejabelajar.auth.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public class GetLogsTeacher implements GetLogsBehaviour{
    Teacher teacher;
    public GetLogsTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public List<Log> get(List<Log> logs) {
        return teacher.getLog();
    }
}
