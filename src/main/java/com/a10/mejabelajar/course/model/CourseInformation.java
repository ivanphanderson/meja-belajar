package com.a10.mejabelajar.course.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "courseInformation")
@Data
@NoArgsConstructor
public class CourseInformation {

    @Id
    @Column(name = "courseInformationId", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "courseInformationTitle")
    private String courseInformationTitle;

    @Column(name = "courseInformationBody", columnDefinition = "TEXT")
    private String courseInformationBody;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "course", nullable = false)
    private Course course;
}
