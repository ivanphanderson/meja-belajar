package com.a10.mejabelajar.admin.util;

import com.a10.mejabelajar.admin.util.behaviour.GetLogsTeacher;
import com.a10.mejabelajar.auth.model.Teacher;


public class TeacherStrategy extends Strategy {
    Teacher teacher;

    public TeacherStrategy(Teacher teacher) {
        this.teacher = teacher;
        getLogsBehaviour = new GetLogsTeacher(teacher);
    }
}
