package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.CreateAdminDTO;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.model.CreateStudentAndTeacherDTO;

public interface RegistrationService {
    User createUser(CreateAdminDTO dto);
    User createUser(CreateStudentAndTeacherDTO dto);
}
