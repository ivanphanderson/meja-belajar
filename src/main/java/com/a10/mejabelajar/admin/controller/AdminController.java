package com.a10.mejabelajar.admin.controller;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.admin.service.ActivationService;
import com.a10.mejabelajar.admin.service.LogService;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private LogService logService;

    @Autowired
    private ActivationService activationService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/form-log")
    public String formLog(Model model) {
        model.addAttribute("students", studentService.getStudents());
        model.addAttribute("newLog", new Log());
        return "admin/formLog";
    }

    @PostMapping(value = "/form-log")
    public String formLog(@AuthenticationPrincipal User user,
                          @RequestParam double hour,
                          @RequestParam String desc,
                          @RequestParam(value = "studentId") String studentId) {
        var student = studentService.getStudentById(studentId);
        var teacher = teacherService.getTeacherByUser(user);
        System.out.println("asu");
        logService.createLog(hour, desc, student, teacher);
        return "redirect:/admin/logs";
    }

    @GetMapping(value = "/logs")
    public String getLogs(Model model) {
        model.addAttribute("logs", logService.getLogs());
        return "admin/logs";
    }

    @GetMapping(value = "/log/{logId}/delete-log")
    public String deleteLog(@PathVariable String logId) {
        logService.deleteLog(logId);
        return "redirect:/admin/logs";
    }

    @GetMapping(value = "/user-activation")
    public String activation(Model model) {
        model.addAttribute("users", activationService.notActiveUsers());
        return "admin/userActivation";
    }

    @GetMapping(value = "/{userId}/user-activation")
    public String updateActivation(@PathVariable String userId) {
        var user = userService.getUserById(userId);
        activationService.activateUser(user);
        return "redirect:/admin/user-activation";
    }

}
