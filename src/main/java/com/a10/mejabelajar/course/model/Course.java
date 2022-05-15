package com.a10.mejabelajar.course.model;

import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.murid.model.Murid;
import com.a10.mejabelajar.murid.model.Rate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
public class Course {

    @Id
    @Column(name = "courseId", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "courseName")
    private String courseName;

    @Column(name = "courseType")
    private CourseType courseType;

    @Column(name = "courseDescription")
    private String courseDescription;

    @Column(name = "courseDuration")
    private double courseDuration;

    @Column(name = "archived")
    private boolean archived = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseInformation> courseInformations;

    @JsonIgnore
    @ManyToMany(mappedBy = "newCourse", cascade = CascadeType.ALL)
    private List<Murid> newMurid = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Rate> newRate;
}
