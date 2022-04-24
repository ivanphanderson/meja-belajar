package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.model.UserDTO;

public interface RegistrationService {
    User createUser(UserDTO dto);
    void validateTeacherAndStudentRegistration(UserDTO dto);
}
