package com.a10.mejabelajar.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @RequestMapping(method = RequestMethod.GET, path = "")
    public String createCourse() {
        return "admin/formLog";
    }
}
