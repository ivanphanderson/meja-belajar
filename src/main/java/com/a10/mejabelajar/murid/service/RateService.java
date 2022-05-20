package com.a10.mejabelajar.murid.service;

import com.a10.mejabelajar.murid.model.Rate;

import java.util.List;

public interface RateService {
    Rate createRate(String id, Integer courseId, Integer rate);
    List<Rate> getListRate();
}
