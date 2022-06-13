package com.a10.mejabelajar.admin.controller;

import com.a10.mejabelajar.admin.exception.LogInvalidException;
import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.admin.service.ActivationService;
import com.a10.mejabelajar.admin.service.LogService;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private final String ERROR = "error";
    private final String REDIRECT_LOGS = "redirect:/admin/logs";

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

    /**
     * Show form log.
     */
    @GetMapping(value = "/form-log")
    public String formLog(Model model) {
        model.addAttribute("students", studentService.getStudents());
        model.addAttribute("newLog", new Log());
        return "admin/formLog";
    }

    /**
     * Post form log.
     */
    @PostMapping(value = "/form-log")
    public String formLog(@RequestParam String start,
                          @RequestParam String end,
                          @RequestParam String desc,
                          @RequestParam(value = "studentId") String studentId,
                          Model model) {
        var user = getPrincipalUser();
        var student = CompletableFuture.supplyAsync(() -> studentService.getStudentById(studentId));
        var teacher = CompletableFuture.supplyAsync(() -> teacherService.getTeacherByUser(user));
        var students = CompletableFuture.supplyAsync(() -> studentService.getStudents());

        try{
            String duration = logService.countDuration(start, end);
            logService.createLog(start, end, duration, desc, student.join(), teacher.join());
        } catch (LogInvalidException e) {
            model.addAttribute(ERROR, e.getMessage());
            model.addAttribute("students", students.join());
            model.addAttribute("newLog", new Log());
            return "admin/formLog";
        } catch (Exception e) {
            model.addAttribute(ERROR, e);
            return "admin/errorPage";
        }
        return REDIRECT_LOGS;
    }

    /**
     * Get user log.
     */
    @GetMapping(value = "/logs")
    public String getLogs(Model model) {
        var user = getPrincipalUser();
        try{
            var logs = logService.getLogs(user);
            model.addAttribute("role", user.getRole());
            model.addAttribute("logs", logs);
            model.addAttribute("username", user.getUsername());
            return "admin/logs";
        } catch (Exception e) {
            model.addAttribute(ERROR, e);
            return "admin/errorPage";
        }

    }

    @GetMapping(value = "/log/{logId}/delete-log")
    public String deleteLog(@PathVariable String logId) {
        logService.deleteLog(logId);
        return REDIRECT_LOGS;
    }

    @GetMapping(value = "/user-activation")
    public String activation(Model model) {
        model.addAttribute("users", activationService.notActiveUsers());
        return "admin/userActivation";
    }

    /**
     * Update user activation.
     */
    @GetMapping(value = "/{userId}/user-activation")
    public String updateActivation(@PathVariable String userId) {
        var user = userService.getUserById(userId);
        activationService.activateUser(user);
        return "redirect:/admin/user-activation";
    }

    /**
     * Do log payment.
     */
    @GetMapping(value = "/log/{logId}/bayar")
    public String bayarLog(@PathVariable String logId) {
        var log = logService.getLogById(logId);
        logService.bayarLog(log);
        return REDIRECT_LOGS;
    }

    /**
     * Verify log.
     */
    @GetMapping(value = "/log/{logId}/verifikasi")
    public String verifikasiLog(@PathVariable String logId) {
        var log = logService.getLogById(logId);
        logService.verifikasiLog(log);
        return REDIRECT_LOGS;
    }

    private User getPrincipalUser() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userDetails = (UserDetails) principal;
        var user = userService.getUserByUsername(userDetails.getUsername());
        return user;
    }
}
