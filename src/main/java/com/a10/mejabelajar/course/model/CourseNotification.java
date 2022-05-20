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

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private CourseInformation courseInformation;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course", nullable = false)
    private Student student;
}
