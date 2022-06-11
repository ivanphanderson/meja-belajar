package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.CreateAdminDTO;
import com.a10.mejabelajar.auth.model.CreateStudentAndTeacherDTO;
import com.a10.mejabelajar.auth.model.User;

public interface RegistrationService {
    User createUser(CreateAdminDTO dto);

    User createUser(CreateStudentAndTeacherDTO dto);
}
