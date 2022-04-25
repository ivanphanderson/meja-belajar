package com.a10.mejabelajar.admin.model;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log")
public class Log {

    @Id
    @Column(name="log_id")
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy= "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "hours")
    private double hour;

    @Column(name = "description")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
