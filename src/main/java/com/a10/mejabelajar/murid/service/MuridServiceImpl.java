package com.a10.mejabelajar.murid.service;

import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.murid.model.Murid;
import com.a10.mejabelajar.murid.repository.MuridRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MuridServiceImpl implements MuridService{

    @Autowired
    private MuridRepository muridRepository;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Murid regisMurid(Murid murid) {
        muridRepository.save(murid);
        return murid;
    }

    /**
     * Tambahin murid ke course.
     */
    @Override
    public Murid updateMurid(int id, Murid murid) {
        murid.setId(id);
        murid.setMuridName(muridRepository.findById(id).getMuridName());
        murid.setMuridType(muridRepository.findById(id).getMuridType());
        murid.setCourse(courseRepository.findById(murid.idCourse));
        muridRepository.save(murid);
        return murid;
    }

    public Murid getMuridById(int id) {
        return muridRepository.findById(id);
    }
}
