package com.a10.mejabelajar.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    private static final String MOCK_USERNAME = "user";
    private static final String MOCK_EMAIL = "user@mail.com";
    private static String MOCK_PASSWORD = "abc123";
    private static final Role STUDENT_ROLE = Role.STUDENT;
    private User mockUserStudent;

    @BeforeEach
    void setUp() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(MOCK_PASSWORD);
        mockUserStudent = new User(MOCK_USERNAME, MOCK_EMAIL, encodedPassword, STUDENT_ROLE);
    }

    @Test
    void testloadUserByUsernameReturnsUser() {
        when(userRepository.findByUsername(MOCK_USERNAME)).thenReturn(mockUserStudent);
        UserDetails user = userDetailsService.loadUserByUsername(MOCK_USERNAME);
        assertEquals(mockUserStudent.getUsername(), user.getUsername());
    }

    @Test
    void testloadUserByUsernameThrowsException() {
        when(userRepository.findByUsername(MOCK_USERNAME)).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(MOCK_USERNAME));
    }
}
