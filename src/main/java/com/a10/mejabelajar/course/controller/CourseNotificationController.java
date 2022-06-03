package com.a10.mejabelajar.course.controller;

import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.auth.service.UserService;
import com.a10.mejabelajar.course.model.CourseNotification;
import com.a10.mejabelajar.course.service.CourseNotificationService;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping(path = "/course/notification")
public class CourseNotificationController {

    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseNotificationService courseNotificationService;

    private static final String ERROR = "error";
    private static final String REDIRECT_COURSE = "redirect:/course/";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final long HOUR = 3600L * 1000; // in milli-seconds.

    /**
     * Show Notification.
     */
    @GetMapping(value = "")
    public String courseNotification(
            Model model,
            RedirectAttributes redirectAttrs) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return REDIRECT_LOGIN;
        }
        var userDetails = (UserDetails) principal;
        var user = userService.getUserByUsername(userDetails.getUsername());

        if (user.getRole() == Role.STUDENT) {
            var student = studentService.getStudentByUser(user);

            var instant = Instant.now();
            var date = Date.from(instant);
            var newDate = new Date(date.getTime() + 7 * HOUR);

            List<CourseNotification> courseNotifications =
                    courseNotificationService
                            .getCourseNotificationByStudentAndCreatedAtIsGreaterThanEqual(
                                    student,
                                    student.getLastNotifBtnClick()
                            );

            List<CourseNotification> courseNotifications1 =
                    courseNotificationService
                            .getCourseNotificationByStudentAndCreatedAtIsLessThan(
                                    student,
                                    student.getLastNotifBtnClick()
                            );

            studentService.setStudentLastNotifBtnClick(student, newDate);
            model.addAttribute("courseNotifications", courseNotifications);
            model.addAttribute("courseNotifications1", courseNotifications1);
            return "course/courseNotification";
        } else {
            redirectAttrs.addFlashAttribute(ERROR,
                    "The feature is available only for student");
            return REDIRECT_COURSE;
        }
    }
}
