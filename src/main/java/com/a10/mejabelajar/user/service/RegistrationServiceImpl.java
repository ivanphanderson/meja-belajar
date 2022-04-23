package com.a10.mejabelajar.user.service;

import com.a10.mejabelajar.user.model.Student;
import com.a10.mejabelajar.user.model.Teacher;
import com.a10.mejabelajar.user.model.User;
import com.a10.mejabelajar.user.repository.StudentRepository;
import com.a10.mejabelajar.user.repository.TeacherRepository;
import com.a10.mejabelajar.user.repository.UserRepository;
import com.a10.mejabelajar.user.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Override
    public User createUser(User user, String role) throws DataIntegrityViolationException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setActivated(false);
        if(role.equals("teacher")){
            return createTeacher(user);
        }
        else if(role.equals("student")){
            return createStudent(user);
        }
        return null;
    }

    public User createTeacher(User user){
        user.setRole(Role.TEACHER);
        userRepository.save(user);
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacherRepository.save(teacher);
        return user;
    }

    public User createStudent(User user){
        user.setRole(Role.STUDENT);
        userRepository.save(user);
        Student student = new Student();
        student.setUser(user);
        studentRepository.save(student);
        return user;
    }
}
