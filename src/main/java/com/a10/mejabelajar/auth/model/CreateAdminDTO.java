package com.a10.mejabelajar.auth.model;

import lombok.Data;

@Data
public class CreateAdminDTO {
    String username;
    String email;
    String password;
    String token;
}
