package com.a10.mejabelajar.murid.controller;

import com.a10.mejabelajar.murid.model.Murid;
import com.a10.mejabelajar.murid.service.MuridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/murid")
public class MuridController {

    @Autowired
    MuridService muridService;

    @PostMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity registrationMurid(@RequestBody Murid murid) {
        return ResponseEntity.ok(muridService.regisMurid(murid));
    }

    @PutMapping(path = "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity updateMurid(@PathVariable(value = "id") int id, @RequestBody Murid murid) {
        return ResponseEntity.ok(muridService.updateMurid(id, murid));
    }
}