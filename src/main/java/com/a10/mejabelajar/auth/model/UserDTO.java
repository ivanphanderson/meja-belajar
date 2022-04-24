package com.a10.mejabelajar.auth.model;

import lombok.Data;

@Data
public class UserDTO {
    String username;
    String email;
    String password;
    String role;
}
