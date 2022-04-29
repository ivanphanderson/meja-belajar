package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUserById(String userId);
}
