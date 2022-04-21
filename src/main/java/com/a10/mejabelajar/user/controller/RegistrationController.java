package com.a10.mejabelajar.user.controller;

import com.a10.mejabelajar.user.model.user.User;
import com.a10.mejabelajar.user.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    @PostMapping(path = "", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity registerUser(@RequestBody User user, @RequestParam(value = "role") String role) {
        try{
            return ResponseEntity.ok(registrationService.createUser(user, role));
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}