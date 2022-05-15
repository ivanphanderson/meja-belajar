package com.a10.mejabelajar.admin.util;

import com.a10.mejabelajar.admin.util.Behaviour.GetLogsStudent;
import com.a10.mejabelajar.auth.model.Student;

public class StudentStrategy extends Strategy {
    Student student;

    public StudentStrategy(Student student) {
        this.student = student;
        getLogsBehaviour = new GetLogsStudent(student);
    }
}
