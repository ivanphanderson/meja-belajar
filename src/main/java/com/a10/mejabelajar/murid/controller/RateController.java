package com.a10.mejabelajar.murid.controller;

import com.a10.mejabelajar.murid.model.Rate;
import com.a10.mejabelajar.murid.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/rate")
public class RateController {

    @Autowired
    RateService rateService;

    @PostMapping(path = "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity createRate(@PathVariable(value = "id") String id, @RequestBody Rate rate) {
        return ResponseEntity.ok(rateService.createRate(id, rate));
    }
}