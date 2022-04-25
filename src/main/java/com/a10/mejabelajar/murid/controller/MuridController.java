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

//    private static final String MURID = "murid";
//    private static final String MURID_ID = "muridId";
//    private static final String MURID_TYPES = "muridTypes";
//    private static final String ERROR = "error";
//    private static final String REDIRECT_COURSE = "redirect:/course/";
//    private ModelMapper modelMapper = new ModelMapper();

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