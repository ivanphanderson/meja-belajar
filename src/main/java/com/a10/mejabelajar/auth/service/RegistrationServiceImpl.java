package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.exception.InvalidRoleException;
import com.a10.mejabelajar.auth.exception.InvalidTokenException;
import com.a10.mejabelajar.auth.exception.RegistrationFieldEmptyException;
import com.a10.mejabelajar.auth.exception.UsernameOrPasswordAlreadyUsedException;
import com.a10.mejabelajar.auth.model.*;
import com.a10.mejabelajar.auth.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService{
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

    @Override
    public void validateTeacherAndStudentRegistration(CreateStudentAndTeacherDTO dto) {
        if(!validateNoFieldEmpty(dto)){
            throw new RegistrationFieldEmptyException("All field must not be empty");
        }
        if(!validateUsernameNotAlreadyUsed(dto.getUsername())){
            throw new UsernameOrPasswordAlreadyUsedException("Username already used");
        }
        if(!validateEmailNotAlreadyUsed(dto.getEmail())){
            throw new UsernameOrPasswordAlreadyUsedException("Email already used");
        }
        if(!validateRole(dto.getRole())){
            throw new InvalidRoleException("Choose a valid role");
        }
    }

    private boolean validateUsernameNotAlreadyUsed(String username){
        return userRepository.findByUsername(username) == null;
    }

    private boolean validateEmailNotAlreadyUsed(String email){
        return userRepository.findByEmail(email) == null;
    }

    private boolean validateNoFieldEmpty(CreateStudentAndTeacherDTO dto){
        if(dto.getUsername().equals("")){
            return false;
        }
        if(dto.getEmail().equals("")){
            return false;
        }
        if(dto.getPassword().equals("")){
            return false;
        }
        return !dto.getRole().equals("");
    }

    private boolean validateNoFieldEmpty(CreateAdminDTO dto){
        if(dto.getUsername().equals("")){
            return false;
        }
        if(dto.getEmail().equals("")){
            return false;
        }
        if(dto.getPassword().equals("")){
            return false;
        }
        return !dto.getToken().equals("");
    }

    private boolean validateRole(String role){
        return role.equals("student") || role.equals("teacher");
    }

    @Override
    public User createUser(CreateStudentAndTeacherDTO dto){
        validateTeacherAndStudentRegistration(dto);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getUsername(), dto.getEmail(), encodedPassword, Role.valueOf(dto.getRole().toUpperCase()));
        userRepository.save(user);

        if(dto.getRole().equals("student")){
            createStudent(user);
        }
        else if(dto.getRole().equals("teacher")){
            createTeacher(user);
        }

        return user;
    }

    private void validateAdminRegistration(CreateAdminDTO dto){
        if(!validateNoFieldEmpty(dto)){
            throw new RegistrationFieldEmptyException("All field must not be empty");
        }
        if(!validateUsernameNotAlreadyUsed(dto.getUsername())){
            throw new UsernameOrPasswordAlreadyUsedException("Username already used");
        }
        if(!validateEmailNotAlreadyUsed(dto.getEmail())){
            throw new UsernameOrPasswordAlreadyUsedException("Email already used");
        }
        if(!validateToken(dto)){
            throw new InvalidTokenException("Invalid token");
        }
    }

    private boolean validateToken(CreateAdminDTO dto){
        Token token = tokenRepository.findByToken(dto.getToken());
        return token != null && token.isActive();
    }

    @Override
    public User createUser(CreateAdminDTO dto) {
        validateAdminRegistration(dto);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getUsername(), dto.getEmail(), encodedPassword, Role.ADMIN);
        userRepository.save(user);

        tokenRepository.findByToken(dto.getToken()).setActive(false);

        createAdmin(user);

        return user;
    }

    private void createAdmin(User user){
        Admin admin = new Admin();
        admin.setUser(user);
        adminRepository.save(admin);
    }

    private void createTeacher(User user){
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacherRepository.save(teacher);
    }

    private void createStudent(User user){
        Student student = new Student();
        student.setUser(user);
        studentRepository.save(student);
    }
}
