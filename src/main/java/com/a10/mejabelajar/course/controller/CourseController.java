package com.a10.mejabelajar.course.controller;

import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.course.exception.CourseInvalidException;
import com.a10.mejabelajar.course.model.*;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import com.a10.mejabelajar.course.service.CourseInformationService;
import com.a10.mejabelajar.course.service.CourseService;
import java.util.List;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper = new ModelMapper();

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
        if (teacher.getCourse() != null) {
            return REDIRECT_COURSE + teacher.getCourse().getId();
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
        if (teacher.getCourse() != null) {
            return REDIRECT_COURSE + teacher.getCourse().getId();
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
        if (teacher.getCourse() != course) {
            return REDIRECT_COURSE + teacher.getCourse().getId();
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
        var teacherCourse = teacher.getCourse();
        if (teacherCourse == null) {
            return REDIRECT_COURSE + teacher.getCourse().getId();
        } else {
            if (teacherCourse.getId() != id) {
                return REDIRECT_COURSE + teacher.getCourse().getId();
            }
        }

        try {
            courseService.updateCourse(
                    id,
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

    /**
     * Show all course.
     */
    @GetMapping(value = "")
    public String readCourse(Model model) {
        List<Course> courses = courseService.getCourses();
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
            Model model) {
        var course = courseService.getCourseById(courseId);
        List<CourseInformation> courseInformations =
                courseInformationService.getCourseInformationByCourse(course);

        var teacher = teacherService.getTeacherByUser(user);
        if (teacher.getCourse() != course) {
            return REDIRECT_COURSE + teacher.getCourse().getId();
        }

        model.addAttribute(COURSE, course);
        model.addAttribute("courseInformations", courseInformations);
        return "course/readCourseById";
    }

    /**
     * Delete a course.
     */
    @GetMapping(value = "/delete/{courseId}")
    public String deleteCourse(@AuthenticationPrincipal User user, @PathVariable int courseId) {
        var teacher = teacherService.getTeacherByUser(user);
        int teacherCourseId = teacher.getCourse().getId();
        if (teacherCourseId != courseId) {
            return REDIRECT_COURSE + teacherCourseId;
        }
        courseService.deleteCourseById(user, courseId);
        return "redirect:/course";
    }
}
