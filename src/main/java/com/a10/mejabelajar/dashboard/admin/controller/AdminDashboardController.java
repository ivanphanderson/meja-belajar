package com.a10.mejabelajar.dashboard.admin.controller;

import com.a10.mejabelajar.admin.service.ActivationService;
import com.a10.mejabelajar.admin.service.LogService;
import com.a10.mejabelajar.auth.model.AdminRegistrationTokenDto;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.dashboard.admin.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/dashboard/admin")
public class AdminDashboardController {
    @Autowired
    private ActivationService activationService;

    @Autowired
    private LogService logService;

    @Autowired
    private AdminDashboardService dashboardService;


    /**
     * Show admin dashboarde.
     */
    @GetMapping(value = "/")
    public String dashboardAdmin(@AuthenticationPrincipal User user,  Model model) {
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("users", activationService.notActiveUsers());
        model.addAttribute("role", user.getRole());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("logs", logService.getLogs(user));
        model.addAttribute("dto", new AdminRegistrationTokenDto());
        return "dashboard/adminDashboard";
    }

    /**
     * Generate token for admin.
     */
    @PostMapping(value = "/generate-token", produces = {"application/json"})
    @ResponseBody
    public String generateToken(
            @AuthenticationPrincipal User user, AdminRegistrationTokenDto tokenDto, Model model) {
        try {
            dashboardService.generateToken(tokenDto);
            return "{\"success\": \"New token have successfully generated\"}";
        } catch (Exception e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
