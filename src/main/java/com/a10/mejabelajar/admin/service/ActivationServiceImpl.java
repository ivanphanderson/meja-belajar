package com.a10.mejabelajar.admin.service;

import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.UserRepository;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivationServiceImpl implements ActivationService{
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> notActiveUsers() {
        var users = userService.getUsers();
        List<User> userNotActive = new ArrayList<>();
        for(var user : users) {
            if(!user.isActivated()) {
                userNotActive.add(user);
            }
        }
        return userNotActive;
    }

    @Override
    public User activateUser(User user) {
        user.setActivated(true);
        userRepository.save(user);
        return user;
    }
}
