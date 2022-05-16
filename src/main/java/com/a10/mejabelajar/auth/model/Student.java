package com.a10.mejabelajar.auth.model;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.murid.model.MuridType;
import com.a10.mejabelajar.murid.model.Rate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy= "org.hibernate.id.UUIDGenerator")
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Log> log;

    @Column(name = "murid_name")
    private String muridName;

    @Column(name = "murid_type")
    private MuridType muridType;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_taken",
            joinColumns = @JoinColumn(name = "murid_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> newCourse = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Rate> muridRate = new ArrayList<>();

}
