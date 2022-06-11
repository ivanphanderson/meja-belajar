package com.a10.mejabelajar.murid.service;

import com.a10.mejabelajar.auth.model.Student;

public interface MuridService {
    Student regisMurid(Student student);

    Student updateMurid(int id, Student student);
}