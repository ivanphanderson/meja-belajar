package com.a10.mejabelajar.admin.repository;

import com.a10.mejabelajar.admin.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
}
