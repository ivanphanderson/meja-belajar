package com.a10.mejabelajar.murid.controller;

import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.murid.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/course/rate")
public class RateController {

    @Autowired
    RateService rateService;

    /**
     * Rate a course.
     */
    @PostMapping(value = "")
    public String rateCourse(
            @RequestParam Integer rate,
            @RequestParam Integer idCourse,
            RedirectAttributes redirectAttrs
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String studentId = ((User)principal).getId();

        var newRate = rateService.getByIdStudentAndIdCourse(studentId, idCourse);
        if (newRate == null) {
            rateService.createRate(studentId, idCourse, rate);
            return "redirect:/murid";
        } else {
            redirectAttrs.addFlashAttribute("error", "Anda sudah memberikan rate");
            return "redirect:/course" + idCourse;
        }

    }
}
