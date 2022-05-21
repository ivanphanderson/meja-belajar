package com.a10.mejabelajar.auth.service;

import com.a10.mejabelajar.auth.exception.*;
import com.a10.mejabelajar.auth.model.*;
import com.a10.mejabelajar.auth.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

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

    public void validateEmailAndUsername(String email, String username){
        if(!validateUsernameNotAlreadyUsed(username)){
            throw new UsernameOrEmailAlreadyUsedException("Username already used");
        }
        if(!validateEmailPattern(email)){
            throw new InvalidEmailException("Invalid Email");
        }
        if(!validateEmailNotAlreadyUsed(email)){
            throw new UsernameOrEmailAlreadyUsedException("Email already used");
        }
    }

    public void validateAdminRegistration(CreateAdminDTO dto){
        if(!validateNoFieldEmpty(dto)){
            throw new RegistrationFieldEmptyException("All field must not be empty");
        }

        validateEmailAndUsername(dto.getEmail(), dto.getUsername());

        if(!validateToken(dto)){
            throw new InvalidTokenException("Invalid token");
        }
    }

    public void validateTeacherAndStudentRegistration(CreateStudentAndTeacherDTO dto) {
        if(!validateNoFieldEmpty(dto)){
            throw new RegistrationFieldEmptyException("All field must not be empty");
        }

        validateEmailAndUsername(dto.getEmail(), dto.getUsername());

        if(!validateRole(dto.getRole())){
            throw new InvalidRoleException("Choose a valid role");
        }
    }

    public boolean validateUsernameNotAlreadyUsed(String username){
        return userRepository.findByUsername(username) == null;
    }

    public boolean validateEmailNotAlreadyUsed(String email){
        return userRepository.findByEmail(email) == null;
    }

    public boolean validateEmailPattern(String email){
        // Pattern taken from https://www.baeldung.com/java-email-validation-regex
        var p = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        return p.matcher(email).matches();
    }

    public boolean validateNoFieldEmpty(CreateStudentAndTeacherDTO dto){
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

    public boolean validateNoFieldEmpty(CreateAdminDTO dto){
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

    public boolean validateRole(String role){
        return role.equals("student") || role.equals("teacher");
    }

    public boolean validateToken(CreateAdminDTO dto){
        AdminRegistrationToken token = tokenRepository.findByToken(dto.getToken());
        return token != null && token.isActive();
    }

    @Override
    public User createUser(CreateStudentAndTeacherDTO dto){
        validateTeacherAndStudentRegistration(dto);
        var passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        var user = new User(dto.getUsername(), dto.getEmail(), encodedPassword, Role.valueOf(dto.getRole().toUpperCase()));
        userRepository.save(user);

        if(dto.getRole().equals("student")){
            createStudent(user);
        }
        else if(dto.getRole().equals("teacher")){
            createTeacher(user);
        }

        return user;
    }

    @Override
    public User createUser(CreateAdminDTO dto) {
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

    public void createAdmin(User user){
        var admin = new Admin();
        admin.setUser(user);
        adminRepository.save(admin);
    }

    public void createTeacher(User user){
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacherRepository.save(teacher);
    }

    public void createStudent(User user){
        Student student = new Student();
        student.setUser(user);
        studentRepository.save(student);
    }
}
