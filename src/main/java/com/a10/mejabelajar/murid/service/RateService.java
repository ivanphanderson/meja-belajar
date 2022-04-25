package com.a10.mejabelajar.murid.service;

import com.a10.mejabelajar.murid.model.Rate;

public interface RateService {
    Rate createRate(int id, int idMurid, Rate rate);
}
