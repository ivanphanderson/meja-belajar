package com.a10.mejabelajar.course.controller;

import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.auth.service.UserService;
import com.a10.mejabelajar.course.exception.CourseInformationInvalidException;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.model.dto.CourseInformationDataTransferObject;
import com.a10.mejabelajar.course.service.CourseInformationService;
import com.a10.mejabelajar.course.service.CourseNotificationService;
import com.a10.mejabelajar.course.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/course/information/")
public class CourseInformationController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseInformationService courseInformationService;

    @Autowired
    private CourseNotificationService courseNotificationService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    private static final String COURSE_ID = "courseId";
    private static final String COURSE_INFORMATION = "courseInformation";
    private static final String ERROR = "error";
    private static final String UNEXPECTED_ERROR_MSG = "An unexpected error occured";
    private static final String REDIRECT_COURSE = "redirect:/course/";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String COURSE_ERROR_PAGE = "course/courseErrorPage";
    private ModelMapper modelMapper = new ModelMapper();

    /**
     * Show create course information page for specific course specified by id in path variable.
     */
    @GetMapping(value = "/create/{courseId}")
    public String createCourseInformation(
            @PathVariable int courseId,
            Model model,
            RedirectAttributes redirectAttrs) {
        try {
            var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String isValid =
                    validator(principal, courseId, "Create Course Information", redirectAttrs);
            if (!isValid.equals("")) {
                return isValid;
            }

            model.addAttribute(COURSE_ID, courseId);
            model.addAttribute(COURSE_INFORMATION, new CourseInformationDataTransferObject());
            return "course/createCourseInformation";
        } catch (Exception e) {
            model.addAttribute(ERROR, UNEXPECTED_ERROR_MSG);
            return COURSE_ERROR_PAGE;
        }
    }

    /**
     * Create new course information for specific course specified by id in path variable.
     */
    @PostMapping(value = "/create/{courseId}")
    public String createCourseInformation(
            @ModelAttribute CourseInformationDataTransferObject courseInformationDataTransferObject,
            @PathVariable int courseId,
            Model model,
            RedirectAttributes redirectAttrs) {
        try {
            var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            var course = courseService.getCourseById(courseId);
            String isValid =
                    validator(principal, courseId, "Create Course Information", redirectAttrs);
            if (!isValid.equals("")) {
                return isValid;
            }

            var courseInformation = new CourseInformation();
            modelMapper.map(courseInformationDataTransferObject, courseInformation);

            courseInformation.setCourse(course);
            var courseInformation1 =
                    courseInformationService.createCourseInformation(courseInformation);
            courseNotificationService.handleCreateInformation(courseInformation1);
            return REDIRECT_COURSE + courseId;
        } catch (CourseInformationInvalidException e) {
            model.addAttribute(ERROR, e.getMessage());
            model.addAttribute(COURSE_ID, courseId);
            model.addAttribute(COURSE_INFORMATION, courseInformationDataTransferObject);
            return "course/createCourseInformation";
        } catch (Exception e) {
            model.addAttribute(ERROR, UNEXPECTED_ERROR_MSG);
            return COURSE_ERROR_PAGE;
        }
    }

    /**
     * Update a course information specified by course information id in path variable.
     */
    @GetMapping(value = "/update/{courseId}/{courseInformationId}")
    public String updateCourseInformation(
            @PathVariable int courseId,
            @PathVariable int courseInformationId,
            Model model,
            RedirectAttributes redirectAttrs) {
        try {
            var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String isValid =
                    validator(principal, courseId, "Update Course Information", redirectAttrs);
            if (!isValid.equals("")) {
                return isValid;
            }

            var courseInformation =
                    courseInformationService.getCourseInformationById(courseInformationId);
            model.addAttribute(COURSE_ID, courseId);
            model.addAttribute(COURSE_INFORMATION, courseInformation);
            model.addAttribute("courseInformationId", courseInformationId);
            return "course/updateCourseInformation";
        } catch (Exception e) {
            model.addAttribute(ERROR, UNEXPECTED_ERROR_MSG);
            return COURSE_ERROR_PAGE;
        }
    }

    /**
     * Update course information for specific course specified by id in path variable.
     */
    @PostMapping(value = "/update/{courseId}/{courseInformationId}")
    public String updateCourseInformation(
            @ModelAttribute CourseInformationDataTransferObject courseInformationDataTransferObject,
            @PathVariable int courseId,
            @PathVariable int courseInformationId,
            Model model,
            RedirectAttributes redirectAttrs) {
        try {
            var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String isValid =
                    validator(principal, courseId, "Update Course Information", redirectAttrs);
            if (!isValid.equals("")) {
                return isValid;
            }

            var courseInformation = new CourseInformation();
            modelMapper.map(courseInformationDataTransferObject, courseInformation);

            courseInformationService.updateCourseInformation(
                    courseInformationId,
                    courseInformation
            );
            courseNotificationService.handleUpdateInformation(courseInformation);
            return REDIRECT_COURSE + courseId;
        } catch (CourseInformationInvalidException e) {
            model.addAttribute(ERROR, e.getMessage());
            model.addAttribute(COURSE_ID, courseId);
            model.addAttribute("courseInformationId", courseInformationId);
            model.addAttribute(COURSE_INFORMATION, courseInformationDataTransferObject);
            return "course/updateCourseInformation";
        } catch (Exception e) {
            model.addAttribute(ERROR, UNEXPECTED_ERROR_MSG);
            return COURSE_ERROR_PAGE;
        }
    }

    /**
     * Delete course information for specific course specified by id in path variable.
     */
    @PostMapping(value = "/delete/{courseId}/{courseInformationId}")
    public String deleteCourseInformation(
            @PathVariable int courseId,
            @PathVariable int courseInformationId,
            RedirectAttributes redirectAttrs,
            Model model) {
        try {
            var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String isValid =
                    validator(principal, courseId, "Delete Course Information", redirectAttrs);
            if (!isValid.equals("")) {
                return isValid;
            }
            courseInformationService.deleteCourseInformationById(courseInformationId);
            return REDIRECT_COURSE + courseId;
        } catch (Exception e) {
            model.addAttribute(ERROR, UNEXPECTED_ERROR_MSG);
            return COURSE_ERROR_PAGE;
        }
    }

    private String validator(
            Object principal,
            int courseId,
            String action,
            RedirectAttributes redirectAttrs) {
        if (principal instanceof String) {
            return REDIRECT_LOGIN;
        }
        var userDetails = (UserDetails) principal;
        var user = userService.getUserByUsername(userDetails.getUsername());

        var teacher = teacherService.getTeacherByUser(user);
        var course = courseService.getCourseById(courseId);
        String isValid =
                validateTeacherAccess(teacher, course, action, redirectAttrs);
        if (!isValid.equals("")) {
            return isValid;
        }
        return "";
    }

    private String validateTeacherAccess(
            Teacher teacher,
            Course course,
            String action,
            RedirectAttributes redirectAttrs) {
        if (course.isArchived() && course.getTeacher() == teacher) {
            redirectAttrs.addFlashAttribute(ERROR, "This course is archived");
            return REDIRECT_COURSE + course.getId();
        }
        if (course.getTeacher() != teacher) {
            if (!teacher.isHaveCourse()) {
                redirectAttrs.addFlashAttribute(ERROR, "You don't have access to " + action);
                return "redirect:/dashboard/teacher/";
            }
            redirectAttrs.addFlashAttribute(ERROR, "You don't have access to " + action);
            return REDIRECT_COURSE
                    + courseService.getCourseByTeacherAndStatus(teacher, false).getId();
        }
        return "";
    }
}
