package com.a10.mejabelajar.user.model.user;

import com.a10.mejabelajar.user.model.Admin;
import com.a10.mejabelajar.user.model.Student;
import com.a10.mejabelajar.user.model.Teacher;
import com.a10.mejabelajar.user.util.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer","teacher", "student", "admin"})
public class User {
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy= "org.hibernate.id.UUIDGenerator")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable=false)
    private Role role;

    @Column(name = "username", nullable=false, unique = true)
    private String username;

    @Column(name = "email", nullable=false, unique = true)
    private String email;

    @Column(name = "password", nullable=false)
    private String password;

    @Column(name = "isActivated")
    private boolean isActivated;

    @OneToOne(mappedBy = "user")
    private Teacher teacher;

    @OneToOne(mappedBy = "user")
    private Student student;

    @OneToOne(mappedBy = "user")
    private Admin admin;
}
