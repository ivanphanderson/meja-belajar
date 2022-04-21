package com.a10.mejabelajar.user.repository;

import com.a10.mejabelajar.user.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
