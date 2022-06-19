package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.CreateAdminDto;
import com.a10.mejabelajar.auth.model.CreateStudentAndTeacherDto;
import com.a10.mejabelajar.auth.model.User;

public interface RegistrationService {
    User createUser(CreateAdminDto dto);

    User createUser(CreateStudentAndTeacherDto dto);
}
