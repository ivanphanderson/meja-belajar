package com.a10.mejabelajar.course.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    public Course(String courseName, CourseType courseType, String courseDescription, double courseDuration) {
        this.courseName = courseName;
        this.courseType = courseType;
        this.courseDescription = courseDescription;
        this.courseDuration = courseDuration;
    }
}
