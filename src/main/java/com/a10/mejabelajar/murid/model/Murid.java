package com.a10.mejabelajar.murid.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import com.a10.mejabelajar.course.model.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "murid")
@Data
@NoArgsConstructor
public class Murid {

    @Id
    @Column(name = "murid_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "murid_name")
    private String muridName;

    @Column(name = "murid_type")
    private MuridType muridType;

    @Column(name = "id_course")
    public int idCourse;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_taken",
            joinColumns = @JoinColumn(name = "murid_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> newCourse = new ArrayList<>();

    /**
     * Constructor to create murid.
     */
    public Murid(String muridName,
                 MuridType muridType) {
        this.muridName = muridName;
        this.muridType = muridType;
    }
    public Murid(int idCourse) {
        this.idCourse = idCourse;
    }

    public void setCourse(Course course) {
        newCourse.add(course);
    }
}
