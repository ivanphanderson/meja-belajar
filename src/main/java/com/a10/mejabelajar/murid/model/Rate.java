package com.a10.mejabelajar.murid.model;

import javax.persistence.*;

import com.a10.mejabelajar.auth.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rate")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Rate {

    private int idCourse;

    @Id
    @Column(name = "rate_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nilai_rating")
    public int nilaiRating;

    @Column(name = "id_murid")
    public String idStudent;

    public Rate(int rating) {
        this.nilaiRating = rating;
    }
}