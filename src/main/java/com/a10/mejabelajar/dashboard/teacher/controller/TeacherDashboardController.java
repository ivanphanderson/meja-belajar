package com.a10.mejabelajar.dashboard.teacher.controller;

import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.dashboard.teacher.service.TeacherDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "/dashboard/teacher")
public class TeacherDashboardController {
    @Autowired
    private TeacherDashboardService dashboardService;

    @Autowired
    private TeacherService teacherService;

    /**
     * Show teacher dashboard.
     */
    @GetMapping(value = "/")
    public String teacherDashboard(@AuthenticationPrincipal User user, Model model) {
        Teacher teacher = teacherService.getTeacherByUser(user);
        model.addAttribute("activeCourse", dashboardService.getActiveCourse(teacher));
        model.addAttribute("archivedCourses", dashboardService.getArchivedCourse(teacher));
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "dashboard/teacherDashboard";
    }
}
