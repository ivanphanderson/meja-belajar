package com.a10.mejabelajar.murid.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import com.a10.mejabelajar.course.model.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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


}
