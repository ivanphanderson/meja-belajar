package com.a10.mejabelajar.auth.model;

import com.a10.mejabelajar.admin.model.Log;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "haveCourse")
    private boolean haveCourse = false;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Log> log;
}
