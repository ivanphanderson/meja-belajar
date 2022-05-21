package com.a10.mejabelajar.course.controller;

import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.course.exception.CourseInformationInvalidException;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.model.dto.CourseInformationDataTransferObject;
import com.a10.mejabelajar.course.service.CourseInformationService;
import com.a10.mejabelajar.course.service.CourseNotificationService;
import com.a10.mejabelajar.course.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private static final String COURSE_ID = "courseId";
    private static final String COURSE_INFORMATION = "courseInformation";
    private static final String ERROR = "error";
    private static final String REDIRECT_COURSE = "redirect:/course/";
    private ModelMapper modelMapper = new ModelMapper();

    /**
     * Show create course information page for specific course specified by id in path variable.
     */
    @GetMapping(value = "/create/{courseId}")
    public String createCourseInformation(
            @AuthenticationPrincipal User user,
            @PathVariable int courseId,
            Model model) {
        var teacher = teacherService.getTeacherByUser(user);
        var course = courseService.getCourseById(courseId);
        String isValid = validateTeacherAccess(teacher, course, "Create Course Information");
        if (!isValid.equals("")) {
            return isValid;
        }

        model.addAttribute(COURSE_ID, courseId);
        model.addAttribute(COURSE_INFORMATION, new CourseInformationDataTransferObject());
        return "course/createCourseInformation";
    }

    /**
     * Create new course information for specific course specified by id in path variable.
     */
    @PostMapping(value = "/create/{courseId}")
    public String createCourseInformation(
            @AuthenticationPrincipal User user,
            @ModelAttribute CourseInformationDataTransferObject courseInformationDataTransferObject,
            @PathVariable int courseId,
            Model model) {

        var teacher = teacherService.getTeacherByUser(user);
        var course = courseService.getCourseById(courseId);
        String isValid = validateTeacherAccess(teacher, course, "Create Course Information");
        if (!isValid.equals("")) {
            return isValid;
        }

        try {
            var courseInformation = new CourseInformation();
            modelMapper.map(courseInformationDataTransferObject, courseInformation);

            courseInformation.setCourse(course);
            CourseInformation courseInformation1 =
                    courseInformationService.createCourseInformation(courseInformation);
            courseNotificationService.handleCreateInformation(courseInformation1);
            return REDIRECT_COURSE + courseId;
        } catch (CourseInformationInvalidException e) {
            model.addAttribute(ERROR, e.getMessage());
            model.addAttribute(COURSE_ID, courseId);
            model.addAttribute(COURSE_INFORMATION, courseInformationDataTransferObject);
            return "course/createCourseInformation";
        }
    }

    /**
     * Update a course information specified by course information id in path variable.
     */
    @GetMapping(value = "/update/{courseId}/{courseInformationId}")
    public String updateCourseInformation(
            @AuthenticationPrincipal User user,
            @PathVariable int courseId,
            @PathVariable int courseInformationId,
            Model model) {
        var teacher = teacherService.getTeacherByUser(user);
        var course = courseService.getCourseById(courseId);
        String isValid = validateTeacherAccess(teacher, course, "Update Course Information");
        if (!isValid.equals("")) {
            return isValid;
        }

        var courseInformation =
                courseInformationService.getCourseInformationById(courseInformationId);
        model.addAttribute(COURSE_ID, courseId);
        model.addAttribute(COURSE_INFORMATION, courseInformation);
        model.addAttribute("courseInformationId", courseInformationId);
        return "course/updateCourseInformation";
    }

    /**
     * Update course information for specific course specified by id in path variable.
     */
    @PostMapping(value = "/update/{courseId}/{courseInformationId}")
    public String updateCourseInformation(
            @AuthenticationPrincipal User user,
            @ModelAttribute CourseInformationDataTransferObject courseInformationDataTransferObject,
            @PathVariable int courseId,
            @PathVariable int courseInformationId,
            Model model) {

        var teacher = teacherService.getTeacherByUser(user);
        var course = courseService.getCourseById(courseId);
        String isValid = validateTeacherAccess(teacher, course, "Update Course Information");
        if (!isValid.equals("")) {
            return isValid;
        }

        try {
            var courseInformation = new CourseInformation();
            modelMapper.map(courseInformationDataTransferObject, courseInformation);

            CourseInformation courseInformation1 = courseInformationService.updateCourseInformation(
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
        }
    }

    /**
     * Delete course information for specific course specified by id in path variable.
     */
    @PostMapping(value = "/delete/{courseId}/{courseInformationId}")
    public String deleteCourseInformation(
            @AuthenticationPrincipal User user,
            @PathVariable int courseId,
            @PathVariable int courseInformationId) {
        var teacher = teacherService.getTeacherByUser(user);
        var course = courseService.getCourseById(courseId);
        String isValid = validateTeacherAccess(teacher, course, "Delete Course Information");
        if (!isValid.equals("")) {
            return isValid;
        }
        courseInformationService.deleteCourseInformationById(courseInformationId);
        return REDIRECT_COURSE + courseId;
    }

    /**
     * Validate teacher access to a course information.
     */
    public String validateTeacherAccess(Teacher teacher, Course course, String action) {
        if (course.isArchived()) {
            if (course.getTeacher() == teacher) {
                return REDIRECT_COURSE + course.getId() + "?error=This course is archived";
            }
        }
        if (course.getTeacher() != teacher) {
            if (!teacher.isHaveCourse()) {
                return REDIRECT_COURSE + "?error=You don't have access to " + action;
            }
            return REDIRECT_COURSE
                    + courseService.getCourseByTeacherAndStatus(teacher, false).getId()
                    + "?error=You don't have access to " + action;
        }
        return "";
    }
}
