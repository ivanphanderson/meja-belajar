package com.a10.mejabelajar.murid.model;

import javax.persistence.*;
import com.a10.mejabelajar.course.model.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rate")
@Data
@NoArgsConstructor
public class Rate {

    @Id
    @Column(name = "rate_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "rateMurid", nullable = false)
    private Murid murid;

    @ManyToOne
    @JoinColumn(name = "rateCourse", nullable = false)
    private Course course;

    @Column(name = "nilai_rating")
    public int nilaiRating;
}


