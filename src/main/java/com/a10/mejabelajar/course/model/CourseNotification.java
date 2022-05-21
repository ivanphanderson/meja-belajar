package com.a10.mejabelajar.course.model;

import com.a10.mejabelajar.auth.model.Student;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "courseNotification")
@Data
@NoArgsConstructor
public class CourseNotification {
    @Id
    @Column(name = "courseNotifiacitonId", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "type")
    private NotificationType notificationType;

    @Column(name = "createdAt")
    private Date createdAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "courseInformationId")
    private CourseInformation courseInformation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "studentId", nullable = false)
    private Student student;
}
