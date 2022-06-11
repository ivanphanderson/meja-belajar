package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.TeacherRepository;
import com.a10.mejabelajar.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private static final String USER_ID = "0e403c72-abcd-abcd-abcd-a1450c032b8a";
    private static final String USERNAME = "user1";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
    }

    @Test
    void testGetAllUsers() {
        assertNotNull(userService.getUsers());
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(USER_ID)).thenReturn(user);
        var nUser = userService.getUserById(USER_ID);
        assertEquals(USER_ID, nUser.getId());
    }

    @Test
    void testGetUserByUsername() {
        when(userService.getUserByUsername(USERNAME)).thenReturn(user);
        var nUser = userService.getUserByUsername(USERNAME);
        assertEquals(USERNAME, nUser.getUsername());
    }
}
