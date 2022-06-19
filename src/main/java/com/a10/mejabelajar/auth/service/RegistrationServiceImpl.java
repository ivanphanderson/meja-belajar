package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.exception.*;
import com.a10.mejabelajar.auth.model.*;
import com.a10.mejabelajar.auth.repository.*;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TokenRepository tokenRepository;

    /**
     * Validate email and username.
     */
    public void validateEmailAndUsername(String email, String username) {
        if (!validateUsernameNotAlreadyUsed(username)) {
            throw new UsernameOrEmailAlreadyUsedException("Username already used");
        }
        if (!validateEmailPattern(email)) {
            throw new InvalidEmailException("Invalid Email");
        }
        if (!validateEmailNotAlreadyUsed(email)) {
            throw new UsernameOrEmailAlreadyUsedException("Email already used");
        }
    }

    /**
     * Validate admin registration.
     */
    public void validateAdminRegistration(CreateAdminDto dto) {
        if (!validateNoFieldEmpty(dto)) {
            throw new RegistrationFieldEmptyException("All field must not be empty");
        }

        validateEmailAndUsername(dto.getEmail(), dto.getUsername());

        if (!validateToken(dto)) {
            throw new InvalidTokenException("Invalid token");
        }
    }

    /**
     * Validate teacher and student registration.
     */
    public void validateTeacherAndStudentRegistration(CreateStudentAndTeacherDto dto) {
        if (!validateNoFieldEmpty(dto)) {
            throw new RegistrationFieldEmptyException("All field must not be empty");
        }

        validateEmailAndUsername(dto.getEmail(), dto.getUsername());

        if (!validateRole(dto.getRole())) {
            throw new InvalidRoleException("Choose a valid role");
        }
    }

    public boolean validateUsernameNotAlreadyUsed(String username) {
        return userRepository.findByUsername(username) == null;
    }

    public boolean validateEmailNotAlreadyUsed(String email) {
        return userRepository.findByEmail(email) == null;
    }

    /**
     * Validate email pattern.
     */
    public boolean validateEmailPattern(String email) {
        // Pattern taken from https://www.baeldung.com/java-email-validation-regex
        var p = Pattern.compile(
                "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
                        + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        return p.matcher(email).matches();
    }

    /**
     * Validate no field empty.
     */
    public boolean validateNoFieldEmpty(CreateStudentAndTeacherDto dto) {
        if (dto.getUsername().equals("")) {
            return false;
        }
        if (dto.getEmail().equals("")) {
            return false;
        }
        if (dto.getPassword().equals("")) {
            return false;
        }
        return !dto.getRole().equals("");
    }

    /**
     * Validate no field empty.
     */
    public boolean validateNoFieldEmpty(CreateAdminDto dto) {
        if (dto.getUsername().equals("")) {
            return false;
        }
        if (dto.getEmail().equals("")) {
            return false;
        }
        if (dto.getPassword().equals("")) {
            return false;
        }
        return !dto.getToken().equals("");
    }

    public boolean validateRole(String role) {
        return role.equals("student") || role.equals("teacher");
    }

    public boolean validateToken(CreateAdminDto dto) {
        AdminRegistrationToken token = tokenRepository.findByToken(dto.getToken());
        return token != null && token.isActive();
    }

    @Override
    public User createUser(CreateStudentAndTeacherDto dto) {
        validateTeacherAndStudentRegistration(dto);
        var passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        var user = new User(
                dto.getUsername(),
                dto.getEmail(),
                encodedPassword,
                Role.valueOf(dto.getRole().toUpperCase()));
        userRepository.save(user);

        if (dto.getRole().equals("student")) {
            createStudent(user);
        } else if (dto.getRole().equals("teacher")) {
            createTeacher(user);
        }

        return user;
    }

    @Override
    public User createUser(CreateAdminDto dto) {
        validateAdminRegistration(dto);
        var passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        var user = new User(dto.getUsername(), dto.getEmail(), encodedPassword, Role.ADMIN);
        user.setActivated(true);
        userRepository.save(user);

        tokenRepository.findByToken(dto.getToken()).setActive(false);

        createAdmin(user);

        return user;
    }

    /**
     * Create admin.
     */
    public void createAdmin(User user) {
        var admin = new Admin();
        admin.setUser(user);
        adminRepository.save(admin);
    }

    /**
     * Create teacher.
     */
    public void createTeacher(User user) {
        var teacher = new Teacher();
        teacher.setUser(user);
        teacherRepository.save(teacher);
    }

    /**
     * Create student.
     */
    public void createStudent(User user) {
        var student = new Student();
        student.setUser(user);
        studentRepository.save(student);
    }
}
