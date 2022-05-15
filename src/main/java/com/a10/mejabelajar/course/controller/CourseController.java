package com.a10.mejabelajar.course.controller;

import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.course.exception.CourseInvalidException;
import com.a10.mejabelajar.course.model.*;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import com.a10.mejabelajar.course.service.CourseInformationService;
import com.a10.mejabelajar.course.service.CourseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseInformationService courseInformationService;

    @Autowired
    private TeacherService teacherService;

    private static final String COURSE = "course";
    private static final String COURSE_ID = "courseId";
    private static final String COURSE_TYPES = "courseTypes";
    private static final String ERROR = "error";
    private static final String REDIRECT_COURSE = "redirect:/course/";

    @PostMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    /**
     * Show create course page.
     */
    @GetMapping(path = "/create")
    public String createCourse(@AuthenticationPrincipal User user, Model model) {
        var teacher = teacherService.getTeacherByUser(user);

        if (teacher.isHaveCourse()) {
            return REDIRECT_COURSE
                    + courseService.getCourseByTeacherAndStatus(teacher, false).getId()
                    + "?error=You already have this course, archive this course in order "
                    + "to create a new one";
        }

        model.addAttribute(COURSE_TYPES, CourseType.values());
        model.addAttribute("newCourse", new Course());
        return "course/createCourse";
    }

    /**
     * Create new course.
     */
    @PostMapping(path = "/create")
    public String createCourse(
            @AuthenticationPrincipal User user,
            @ModelAttribute("courseDto") CourseDataTransferObject courseDataTransferObject,
            Model model) {

        var teacher = teacherService.getTeacherByUser(user);
        if (teacher.isHaveCourse()) {
            return REDIRECT_COURSE
                    + courseService.getCourseByTeacherAndStatus(teacher, false).getId()
                    + "?error=You already have this course, archive this course in order "
                    + "to create a new one";
        }

        try {
            courseService.createCourse(courseDataTransferObject, user);
            return "redirect:";
        } catch (CourseInvalidException e) {
            model.addAttribute(ERROR, e.getMessage());
            model.addAttribute(COURSE_TYPES, CourseType.values());
            model.addAttribute("newCourse", courseDataTransferObject);
            return "course/createCourse";
        }
    }

    /**
     * Show update course page.
     */
    @GetMapping(path = "/update/{id}")
    public String updateCourse(
            @AuthenticationPrincipal User user,
            @PathVariable int id, Model model) {
        var course = courseService.getCourseById(id);
        var teacher = teacherService.getTeacherByUser(user);
        String isValid = validateTeacherAccess(teacher, course, "Update The Course");
        if (!isValid.equals("")) {
            return isValid;
        }
        model.addAttribute(COURSE_TYPES, CourseType.values());
        model.addAttribute(COURSE, course);
        model.addAttribute(COURSE_ID, id);
        return "course/updateCourse";
    }

    /**
     * Update course page specified by id in path variable.
     */
    @PostMapping(path = "/update/{id}")
    public String updateCourse(
            @AuthenticationPrincipal User user,
            @ModelAttribute CourseDataTransferObject courseDataTransferObject,
            @PathVariable int id,
            Model model) {

        var teacher = teacherService.getTeacherByUser(user);
        var course = courseService.getCourseById(id);
        String isValid = validateTeacherAccess(teacher, course, "Update the Course");
        if (!isValid.equals("")) {
            return isValid;
        }

        try {
            courseService.updateCourse(
                    id,
                    teacher,
                    courseDataTransferObject
            );
            return REDIRECT_COURSE + id;
        } catch (CourseInvalidException e) {
            model.addAttribute(ERROR, e.getMessage());
            model.addAttribute(COURSE_TYPES, CourseType.values());
            model.addAttribute(COURSE, courseDataTransferObject);
            model.addAttribute(COURSE_ID, id);
            return "course/updateCourse";
        }
    }

    @GetMapping(path = "/error")
    public String teacherIllegalAccess(Model model, String errorMsg) {
        model.addAttribute("error", errorMsg);
        return "course/errorPage";
    }

    /**
     * Show all course.
     */
    @GetMapping(value = "")
    public String readCourse(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "error", required = false) String error,
            Model model) {
        if (user == null) {
            return "redirect:/login";
        }
        List<Course> courses = courseService.   getCourses();
        if (error != null) {
            model.addAttribute("error", error);
        }
        model.addAttribute("courses", courses);
        return "course/readCourse";
    }

    /**
     * Show course page specified by its id in path variable.
     */
    @GetMapping(value = "/{courseId}")
    public String readCourseById(
            @AuthenticationPrincipal User user,
            @PathVariable int courseId,
            @RequestParam(name = "error", required = false) String error,
            Model model) {
        if (user == null) {
            return "redirect:/login";
        }

        var course = courseService.getCourseById(courseId);

        // Add if student
        if (user.getRole() == Role.TEACHER) {
            var teacher = teacherService.getTeacherByUser(user);
            String isValid = validateTeacherAccess(teacher, course, "Read The Course");
            if (!isValid.equals("")) {
                return isValid;
            }
            model.addAttribute("teacher", teacher);
        }


        List<CourseInformation> courseInformations =
                courseInformationService.getCourseInformationByCourse(course);

        if (error != null) {
            model.addAttribute("error", error);
        }
        model.addAttribute(COURSE, course);
        model.addAttribute("courseInformations", courseInformations);
        return "course/readCourseById";
    }

    /**
     * Archive a course.
     */
    @PostMapping(value = "/archive/{courseId}")
    public String archiveCourse(
            @AuthenticationPrincipal User user,
            @PathVariable int courseId,
            Model model) {
        var teacher = teacherService.getTeacherByUser(user);
        var course = courseService.getCourseById(courseId);
        String isValid = validateTeacherAccess(teacher, course, "Archive the Course");
        if (!isValid.equals("")) {
            return isValid;
        }
        courseService.archiveCourseById(user, courseId);
        return REDIRECT_COURSE;
    }

    /**
     * Validate teacher access to a course.
     */
    public String validateTeacherAccess(Teacher teacher, Course course, String action) {
        if (course.isArchived()) {
            if (!action.equals("Read The Course")) {
                if (course.getTeacher() == teacher) {
                    return REDIRECT_COURSE + course.getId() + "?error=This course is archived";
                }
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
