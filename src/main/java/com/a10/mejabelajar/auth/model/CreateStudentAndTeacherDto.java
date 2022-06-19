package com.a10.mejabelajar.auth.model;

import lombok.Data;

@Data
public class CreateStudentAndTeacherDto {
    String username;
    String email;
    String password;
    String role;
}
