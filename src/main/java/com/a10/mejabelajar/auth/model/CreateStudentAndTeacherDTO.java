package com.a10.mejabelajar.auth.model;

import lombok.Data;

@Data
public class CreateStudentAndTeacherDTO {
    String username;
    String email;
    String password;
    String role;
}
