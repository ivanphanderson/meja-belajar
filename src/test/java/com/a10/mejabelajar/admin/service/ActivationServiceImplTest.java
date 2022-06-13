package com.a10.mejabelajar.admin.service;

import com.a10.mejabelajar.admin.repository.LogRepository;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.UserRepository;
import com.a10.mejabelajar.auth.service.UserService;
import com.a10.mejabelajar.auth.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ActivationServiceImplTest {

    @Mock
    LogRepository logRepository;

    private User user;

    @Mock
    UserService userService;

    @InjectMocks
    ActivationServiceImpl activationService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        user = new User();
    }

    @Test
    void testNotActiveUsers() {
        user.setActivated(true);
        List<User> users = List.of(user);
        lenient().when(userService.getUsers()).thenReturn(users);
        List<User> userNotActive = activationService.notActiveUsers();
        assertEquals(userNotActive.size(), 0);
    }

    @Test
    void testActivateUser() {
        user.setActivated(false);
        User userAfterActivate = activationService.activateUser(user);
        assertTrue(userAfterActivate.isActivated());
    }

}
