package com.a10.mejabelajar.course.model;

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
    @Column(name = "course_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_type")
    private CourseType courseType;

    @Column(name = "course_description")
    private String courseDescription;

    @Column(name = "course_duration")
    private double courseDuration;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseInformation> courseInformations;

    @JsonIgnore
    @ManyToMany(mappedBy = "newCourse")
    private List<Murid> newMurid = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Rate> newRate;
}
