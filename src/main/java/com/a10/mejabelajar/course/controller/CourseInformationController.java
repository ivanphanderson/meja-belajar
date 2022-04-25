package com.a10.mejabelajar.course.controller;

import com.a10.mejabelajar.course.exception.CourseInformationInvalidException;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.model.dto.CourseInformationDataTransferObject;
import com.a10.mejabelajar.course.service.CourseInformationService;
import com.a10.mejabelajar.course.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String COURSE_ID = "courseId";
    private static final String COURSE_INFORMATION = "courseInformation";
    private static final String ERROR = "error";
    private static final String REDIRECT_COURSE = "redirect:/course/";
    private ModelMapper modelMapper = new ModelMapper();

    /**
     * Show create course information page for specific course specified by id in path variable.
     */
    @GetMapping(value = "/create/{courseId}")
    public String createCourseInformation(@PathVariable int courseId, Model model) {
        model.addAttribute(COURSE_ID, courseId);
        model.addAttribute(COURSE_INFORMATION, new CourseInformationDataTransferObject());
        return "course/createCourseInformation";
    }

    /**
     * Create new course information for specific course specified by id in path variable.
     */
    @PostMapping(value = "/create/{courseId}")
    public String createCourseInformation(
            @ModelAttribute CourseInformationDataTransferObject courseInformationDataTransferObject,
            @PathVariable int courseId,
            Model model) {
        try {
            var courseInformation = new CourseInformation();
            modelMapper.map(courseInformationDataTransferObject, courseInformation);

            var course = courseService.getCourseById(courseId);
            courseInformation.setCourse(course);
            courseInformationService.createCourseInformation(courseInformation);
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
            @PathVariable int courseId,
            @PathVariable int courseInformationId,
            Model model) {
        var courseInformation =
                courseInformationService.getCourseInformationById(courseInformationId);
        model.addAttribute(COURSE_ID, courseId);
        model.addAttribute(COURSE_INFORMATION, courseInformation);
        model.addAttribute("courseInformationId", courseInformationId);
        return "course/updateCourseInformation";
    }

    /**
     * Create new course information for specific course specified by id in path variable.
     */
    @PostMapping(value = "/update/{courseId}/{courseInformationId}")
    public String updateCourseInformation(
            @ModelAttribute CourseInformationDataTransferObject courseInformationDataTransferObject,
            @PathVariable int courseId,
            @PathVariable int courseInformationId,
            Model model) {
        try {
            var courseInformation = new CourseInformation();
            modelMapper.map(courseInformationDataTransferObject, courseInformation);

            courseInformationService.updateCourseInformation(
                    courseInformationId,
                    courseInformation
            );
            return REDIRECT_COURSE + courseId;
        } catch (CourseInformationInvalidException e) {
            model.addAttribute(ERROR, e.getMessage());
            model.addAttribute(COURSE_ID, courseId);
            model.addAttribute("courseInformationId", courseInformationId);
            model.addAttribute(COURSE_INFORMATION, courseInformationDataTransferObject);
            return "course/updateCourseInformation";
        }
    }

    @GetMapping(value = "/delete/{courseId}/{courseInformationId}")
    public String deleteCourseInformation(
            @PathVariable int courseId,
            @PathVariable int courseInformationId) {
        courseInformationService.deleteCourseInformationById(courseInformationId);
        return REDIRECT_COURSE + courseId;
    }
}
