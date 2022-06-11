package com.a10.mejabelajar.dashboard.student.controller;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.dashboard.student.service.StudentDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/dashboard/student")
public class StudentDashboardController {
    @Autowired
    private StudentDashboardService dashboardService;

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/")
    public String studentDashboard(@AuthenticationPrincipal User user, Model model) {
        if(user == null){
            return "redirect:/login";
        }
        Student student = studentService.getStudentByUser(user);
        model.addAttribute("takenCourses", dashboardService.getTakenCourse(student));
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "dashboard/studentDashboard";
    }
}
