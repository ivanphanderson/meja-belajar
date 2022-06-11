package com.a10.mejabelajar.admin.util.Behaviour;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.auth.model.Student;
import java.util.List;

public class GetLogsStudent implements GetLogsBehaviour {
    Student student;

    public GetLogsStudent(Student student) {
        this.student = student;
    }

    @Override
    public List<Log> get(List<Log> logs) {
        return student.getLog();
    }
}
