package com.a10.mejabelajar.murid.model;

import javax.persistence.*;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.course.model.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rate")
@Data
@NoArgsConstructor
public class Rate {

    private int idCourse;

    @Id
    @Column(name = "rate_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rateMurid", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "rateCourse", nullable = false)
    private Course course;

    @Column(name = "nilai_rating")
    public int nilaiRating;

    public Rate(int rating) {
        this.nilaiRating = rating;
    }

    public void setMurid(Student student, int idCourse) {
        this.student = student;
        this.idCourse = idCourse;
    }
}