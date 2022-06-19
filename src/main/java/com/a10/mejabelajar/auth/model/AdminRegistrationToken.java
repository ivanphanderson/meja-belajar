package com.a10.mejabelajar.auth.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "admin_registration_token")
public class AdminRegistrationToken {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "isActive")
    private boolean isActive;

    public AdminRegistrationToken(String token) {
        this.token = token;
        this.isActive = true;
    }
}
