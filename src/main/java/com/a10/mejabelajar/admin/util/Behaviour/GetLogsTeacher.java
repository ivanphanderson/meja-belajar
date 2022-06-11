package com.a10.mejabelajar.admin.util.Behaviour;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.auth.model.Teacher;
import java.util.List;

public class GetLogsTeacher implements GetLogsBehaviour {
    Teacher teacher;

    public GetLogsTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public List<Log> get(List<Log> logs) {
        return teacher.getLog();
    }
}
