package com.a10.mejabelajar.admin.model;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.Teacher;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log")
public class Log {

    @Id
    @Column(name = "log_id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "starts")
    private LocalDateTime start;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "ends")
    private LocalDateTime end;

    @Column(name = "duration")
    private String duration;

    @Column(name = "description")
    private String desc;

    @Column(name = "status")
    private LogStatus logStatus;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public String getJam() {
        var formatterStart = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy H:mm", new Locale("id"));
        var formatterEnd = DateTimeFormatter.ofPattern("H:mm");
        return start.format(formatterStart) + " - " + end.format(formatterEnd);
    }
}
