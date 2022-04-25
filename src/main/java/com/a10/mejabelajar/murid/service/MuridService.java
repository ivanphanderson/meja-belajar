package com.a10.mejabelajar.murid.service;

import com.a10.mejabelajar.murid.model.Murid;

public interface MuridService {
    Murid regisMurid(Murid murid);
    Murid updateMurid(int id, Murid murid);
}


