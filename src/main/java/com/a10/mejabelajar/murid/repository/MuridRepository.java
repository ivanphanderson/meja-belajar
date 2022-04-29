package com.a10.mejabelajar.murid.repository;

import com.a10.mejabelajar.murid.model.Murid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MuridRepository extends JpaRepository<Murid, Integer> {
    Murid findById(int id);
}
