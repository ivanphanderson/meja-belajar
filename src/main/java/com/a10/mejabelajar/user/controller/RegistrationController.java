package com.a10.mejabelajar.user.controller;

import com.a10.mejabelajar.user.model.UserDTO;
import com.a10.mejabelajar.user.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    @GetMapping({""})
    public String getRegisterPage(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "auth/registration";
    }


    @PostMapping(path = "")
    public String registerUser(UserDTO dto, Model model) {
        try{
            registrationService.createUser(dto);
            model.addAttribute("success", "You have successfully created an account");
            return "auth/login";
        }
        catch (Exception e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("userDto", new UserDTO());
            return "auth/registration";
        }
    }
}