package com.a10.mejabelajar.auth.service;

import static org.mockito.Mockito.*;

import com.a10.mejabelajar.auth.exception.InvalidRoleException;
import com.a10.mejabelajar.auth.exception.InvalidTokenException;
import com.a10.mejabelajar.auth.exception.RegistrationFieldEmptyException;
import com.a10.mejabelajar.auth.exception.UsernameOrEmailAlreadyUsedException;
import com.a10.mejabelajar.auth.model.*;
import com.a10.mejabelajar.auth.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {
    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private TokenRepository tokenRepository;


    private static final String MOCK_USERNAME = "user";
    private static final String MOCK_EMAIL = "user@mail.com";
    private static String MOCK_PASSWORD = "abc123";
    private static final Role STUDENT_ROLE = Role.STUDENT;
    private static final Role TEACHER_ROLE = Role.TEACHER;
    private static final String MOCK_TOKEN = "abcdef";

    private User mockUserStudent;
    private User mockUserAdmin;
    private User mockUserTeacher;
    private AdminRegistrationToken token;
    private CreateStudentAndTeacherDTO studentDTO;
    private CreateStudentAndTeacherDTO teacherDTO;
    private CreateAdminDTO adminDTO;

    @BeforeEach
    void setUp(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(MOCK_PASSWORD);
        mockUserStudent = new User(MOCK_USERNAME, MOCK_EMAIL, encodedPassword, STUDENT_ROLE);
        mockUserTeacher = new User(MOCK_USERNAME, MOCK_EMAIL, encodedPassword, TEACHER_ROLE);
        mockUserAdmin = new User(MOCK_USERNAME, MOCK_EMAIL, encodedPassword, Role.ADMIN);
        token = new AdminRegistrationToken(MOCK_TOKEN);

        studentDTO = new CreateStudentAndTeacherDTO();
        studentDTO.setUsername(MOCK_USERNAME);
        studentDTO.setEmail(MOCK_EMAIL);
        studentDTO.setPassword(MOCK_PASSWORD);
        studentDTO.setRole(STUDENT_ROLE.name().toLowerCase());

        teacherDTO = new CreateStudentAndTeacherDTO();
        teacherDTO.setUsername(MOCK_USERNAME);
        teacherDTO.setEmail(MOCK_EMAIL);
        teacherDTO.setPassword(MOCK_PASSWORD);
        teacherDTO.setRole(TEACHER_ROLE.name().toLowerCase());

        adminDTO = new CreateAdminDTO();
        adminDTO.setUsername(MOCK_USERNAME);
        adminDTO.setEmail(MOCK_EMAIL);
        adminDTO.setPassword(MOCK_PASSWORD);
        adminDTO.setToken(MOCK_TOKEN);
    }

    @Test
    void testCreateStudent(){
        User user = registrationService.createUser(studentDTO);
        verify(userRepository, times(1)).save(any(User.class));
        verify(studentRepository, times(1)).save(any(Student.class));
        assertEquals(mockUserStudent.getUsername(), user.getUsername());
        assertEquals(mockUserStudent.getEmail(), user.getEmail());
        assertEquals(mockUserStudent.getRole(), user.getRole());
    }

    @Test
    void testCreateTeacher(){
        User user = registrationService.createUser(teacherDTO);
        verify(userRepository, times(1)).save(any(User.class));
        verify(teacherRepository, times(1)).save(any(Teacher.class));
        assertEquals(mockUserTeacher.getUsername(), user.getUsername());
        assertEquals(mockUserTeacher.getEmail(), user.getEmail());
        assertEquals(mockUserTeacher.getRole(), user.getRole());
    }

    @Test
    void testCreateAdmin(){
        when(tokenRepository.findByToken(MOCK_TOKEN)).thenReturn(token);
        User user = registrationService.createUser(adminDTO);
        verify(userRepository, times(1)).save(any(User.class));
        verify(adminRepository, times(1)).save(any(Admin.class));
        assertEquals(mockUserAdmin.getUsername(), user.getUsername());
        assertEquals(mockUserAdmin.getEmail(), user.getEmail());
        assertEquals(mockUserAdmin.getRole(),user.getRole());
    }

    @Test
    void testUsernameValidation(){
        when(userRepository.findByUsername(MOCK_USERNAME)).thenReturn(mockUserStudent);
        assertThrows(UsernameOrEmailAlreadyUsedException.class, ()-> registrationService.validateTeacherAndStudentRegistration(studentDTO));
        assertThrows(UsernameOrEmailAlreadyUsedException.class, ()-> registrationService.validateAdminRegistration(adminDTO));
    }

    @Test
    void testEmailValidation(){
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(mockUserStudent);
        assertThrows(UsernameOrEmailAlreadyUsedException.class, ()-> registrationService.validateTeacherAndStudentRegistration(studentDTO));
        assertThrows(UsernameOrEmailAlreadyUsedException.class, ()-> registrationService.validateAdminRegistration(adminDTO));
    }

    @Test
    void testRoleValidation(){
        String role = "admin";
        studentDTO.setRole(role);
        assertThrows(InvalidRoleException.class, ()-> registrationService.validateTeacherAndStudentRegistration(studentDTO));
    }

    @Test
    void testEmptyFieldValidation(){
        studentDTO.setUsername("");
        adminDTO.setUsername("");
        assertThrows(RegistrationFieldEmptyException.class, ()-> registrationService.validateTeacherAndStudentRegistration(studentDTO));
        assertThrows(RegistrationFieldEmptyException.class, ()-> registrationService.validateAdminRegistration(adminDTO));
    }

    @Test
    void testTokenValidation(){
        when(tokenRepository.findByToken(MOCK_TOKEN)).thenReturn(null);
        assertThrows(InvalidTokenException.class, ()-> registrationService.validateAdminRegistration(adminDTO));
    }
}
