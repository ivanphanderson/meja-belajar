package com.a10.mejabelajar.auth.repository;

import com.a10.mejabelajar.auth.model.AdminRegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AdminRegistrationToken, Integer> {
    AdminRegistrationToken findByToken(String token);
}
