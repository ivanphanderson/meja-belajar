package com.a10.mejabelajar.auth.controller;

import com.a10.mejabelajar.auth.model.CreateAdminDTO;
import com.a10.mejabelajar.auth.model.CreateStudentAndTeacherDTO;
import com.a10.mejabelajar.auth.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    private static final String REGISTRATION_VIEW = "auth/registration";
    private static final String ADMIN_REGISTRATION_VIEW = "auth/adminRegistration";

    @GetMapping({""})
    public String getRegisterPage(Model model) {
        model.addAttribute("dto", new CreateStudentAndTeacherDTO());
        return REGISTRATION_VIEW;
    }

    @GetMapping({"/admin"})
    public String getAdminRegisterPage(Model model) {
        model.addAttribute("dto", new CreateAdminDTO());
        return ADMIN_REGISTRATION_VIEW;
    }

    @PostMapping(path = "/admin")
    public String registerAdmin(CreateAdminDTO dto, Model model) {
        try{
            registrationService.createUser(dto);
            model.addAttribute("success", "You have successfully created an account");
            model.addAttribute("dto", new CreateAdminDTO());
            return ADMIN_REGISTRATION_VIEW;
        }
        catch (Exception e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("dto", new CreateAdminDTO());
            return ADMIN_REGISTRATION_VIEW;
        }
    }

    @PostMapping(path = "")
    public String registerUser(CreateStudentAndTeacherDTO dto, Model model) {
        try{
            registrationService.createUser(dto);
            model.addAttribute("success", "You have successfully created an account");
            model.addAttribute("dto", new CreateStudentAndTeacherDTO());
            return REGISTRATION_VIEW;
        }
        catch (Exception e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("dto", new CreateStudentAndTeacherDTO());
            return REGISTRATION_VIEW;
        }
    }
}