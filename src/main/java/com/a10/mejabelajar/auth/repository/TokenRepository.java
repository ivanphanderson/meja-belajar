package com.a10.mejabelajar.auth.repository;

import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByToken(String token);
}
