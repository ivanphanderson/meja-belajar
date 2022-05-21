package com.a10.mejabelajar.course.model;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
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

    @ManyToMany(mappedBy = "newCourse", cascade = CascadeType.ALL)
    private List<Student> newMurid;

    /**
     * Constructor to create course.
     */
    public Course(String courseName,
                  CourseType courseType,
                  String courseDescription,
                  double courseDuration) {
        this.courseName = courseName;
        this.courseType = courseType;
        this.courseDescription = courseDescription;
        this.courseDuration = courseDuration;
    }
}
