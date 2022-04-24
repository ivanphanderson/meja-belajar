package com.a10.mejabelajar.auth.repository;

import com.a10.mejabelajar.auth.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
