package com.a10.mejabelajar.murid.service;

import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.service.CourseService;
import com.a10.mejabelajar.murid.model.Murid;
import com.a10.mejabelajar.murid.model.Rate;
import com.a10.mejabelajar.murid.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService{

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    MuridService muridService;

    @Override
    public Rate createRate(int id, int idMurid, Rate rate) {
        Course newId = courseService.getCourseById(id);
        Murid newId2 = muridService.getMuridById(idMurid);
        System.out.println(newId);
        //System.out.println(newId2);
        rate.setCourse(newId);
        rate.setMurid(newId2);
        rateRepository.save(rate);
        return rate;
    }
}

