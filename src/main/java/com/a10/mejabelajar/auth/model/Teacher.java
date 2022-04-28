package com.a10.mejabelajar.auth.model;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.course.model.Course;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy= "org.hibernate.id.UUIDGenerator")
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Log> log;
}
