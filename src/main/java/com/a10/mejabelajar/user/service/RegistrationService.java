package com.a10.mejabelajar.user.service;

import com.a10.mejabelajar.user.model.User;
import com.a10.mejabelajar.user.model.UserDTO;

public interface RegistrationService {
    User createUser(UserDTO dto);
    void validateTeacherAndStudentRegistration(UserDTO dto);
}
