package com.a10.mejabelajar.admin.service;

import com.a10.mejabelajar.auth.model.User;

import java.util.List;

public interface ActivationService {
    List<User> notActiveUsers();
    User activateUser(User user);
}
