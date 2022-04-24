package com.a10.mejabelajar.course.controller;

import com.a10.mejabelajar.course.exception.CourseInformationInvalidException;
import com.a10.mejabelajar.course.exception.CourseInvalidException;
import com.a10.mejabelajar.course.model.*;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import com.a10.mejabelajar.course.model.dto.CourseInformationDataTransferObject;
import com.a10.mejabelajar.course.service.CourseInformationService;
import com.a10.mejabelajar.course.service.CourseService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String COURSE = "course";
    private static final String COURSE_ID = "courseId";
    private static final String COURSE_INFORMATION = "courseInformation";
    private static final String COURSE_TYPES = "courseTypes";
    private static final String ERROR = "error";
    private static final String REDIRECT_COURSE = "redirect:/course/";
    private ModelMapper modelMapper = new ModelMapper();

    /**
     * Show create course page.
     */
    @GetMapping(path = "/create")
    public String createCourse(Model model) {
        model.addAttribute(COURSE_TYPES, CourseType.values());
        model.addAttribute("newCourse", new Course());
        return "course/createCourse";
    }

    /**
     * Create new course.
     */
    @PostMapping(path = "/create")
    public String createCourse(
            CourseDataTransferObject courseDataTransferObject,
            Model model) {
        try {
            courseService.createCourse(courseDataTransferObject);
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
    public String updateCourse(@PathVariable int id, Model model) {
        var course = courseService.getCourseById(id);
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
            @ModelAttribute CourseDataTransferObject courseDataTransferObject,
            @PathVariable int id,
            Model model) {
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
    public String readCourseById(@PathVariable int courseId, Model model) {
        var course = courseService.getCourseById(courseId);
        List<CourseInformation> courseInformations =
                courseInformationService.getCourseInformationByCourse(course);
        model.addAttribute(COURSE, course);
        model.addAttribute("courseInformations", courseInformations);
        return "course/readCourseById";
    }

    @GetMapping(value = "/delete/{courseId}")
    public String deleteCourse(@PathVariable int courseId) {
        courseService.deleteCourseById(courseId);
        return "redirect:/course";
    }

    /**
     * Show create course information page for specific course specified by id in path variable.
     */
    @GetMapping(value = "/new-information/{courseId}")
    public String createCourseInformation(@PathVariable int courseId, Model model) {
        model.addAttribute(COURSE_ID, courseId);
        model.addAttribute(COURSE_INFORMATION, new CourseInformationDataTransferObject());
        return "course/createCourseInformation";
    }

    /**
     * Create new course information for specific course specified by id in path variable.
     */
    @PostMapping(value = "/new-information/{courseId}")
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
    @GetMapping(value = "/information/update/{courseId}/{courseInformationId}")
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
    @PostMapping(value = "/information/update/{courseId}/{courseInformationId}")
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

    @GetMapping(value = "/information/delete/{courseId}/{courseInformationId}")
    public String deleteCourseInformation(
            @PathVariable int courseId,
            @PathVariable int courseInformationId) {
        courseInformationService.deleteCourseInformationById(courseInformationId);
        return REDIRECT_COURSE + courseId;
    }
}
