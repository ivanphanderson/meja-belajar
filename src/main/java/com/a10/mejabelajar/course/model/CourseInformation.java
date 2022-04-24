package com.a10.mejabelajar.course.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "course_information")
@Data
@NoArgsConstructor
public class CourseInformation {

    @Id
    @Column(name = "course_information_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "course_information_title")
    private String courseInformationTitle;

    @Column(name = "course_information_body", columnDefinition = "TEXT")
    private String courseInformationBody;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "course", nullable = false)
    private Course course;
}
