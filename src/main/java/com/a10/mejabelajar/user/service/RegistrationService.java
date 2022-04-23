package com.a10.mejabelajar.user.service;

import com.a10.mejabelajar.user.model.User;

public interface RegistrationService {
    User createUser(User user, String role);
}
