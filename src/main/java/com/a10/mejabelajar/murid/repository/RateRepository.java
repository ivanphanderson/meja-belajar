package com.a10.mejabelajar.murid.repository;

import com.a10.mejabelajar.murid.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface RateRepository extends JpaRepository<Rate, Integer> {
    Rate findById(int id);

    Rate findByIdStudentAndIdCourse(String idStudent, int idCourse);

    @Query(value = "SELECT AVG(R.nilaiRating) FROM Rate R WHERE R.idCourse= ?1")
    Double findAverageRateByIdCourse(int courseId);
}
