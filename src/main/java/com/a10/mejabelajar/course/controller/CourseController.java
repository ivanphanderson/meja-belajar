package com.a10.mejabelajar.course.controller;

import com.a10.mejabelajar.course.exception.CourseInvalidException;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.model.CourseType;
import com.a10.mejabelajar.course.service.CourseInformationService;
import com.a10.mejabelajar.course.service.CourseService;
import java.util.List;
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

    /**
     * Show create course page.
     */
    @GetMapping(path = "/create")
    public String createCourse(Model model) {
        model.addAttribute("courseTypes", CourseType.values());
        model.addAttribute("newCourse", new Course());
        return "course/createCourse";
    }

    @PostMapping(path = "/create")
    public String createCourse(@RequestParam String courseName,
                               @RequestParam String courseType,
                               @RequestParam String courseDescription,
                               @RequestParam String courseDuration,
                               Model model) {
        try {
            courseService.createCourse(courseName, courseType, courseDescription, courseDuration);
            return "redirect:";
        } catch (CourseInvalidException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("courseTypes", CourseType.values());
            model.addAttribute("newCourse",
                    new Course(courseName, CourseType.IPA, courseDescription, 1));
            return "course/createCourse";
        }
    }

    @GetMapping(path = "/update/{id}")
    public String updateCourse(@PathVariable int id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("courseTypes", CourseType.values());
        model.addAttribute("course", course);
        model.addAttribute("courseId", id);
        return "course/updateCourse";
    }

    @PostMapping(path = "/update/{id}")
    public String updateCourse(@RequestParam String courseName,
                               @RequestParam String courseType,
                               @RequestParam String courseDescription,
                               @RequestParam String courseDuration,
                               @PathVariable int id,
                               Model model) {
        try {
            courseService.updateCourse(
                id,
                courseName,
                courseType,
                courseDescription,
                courseDuration
            );
            return "redirect:/course/" + id;
        } catch (CourseInvalidException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("courseTypes", CourseType.values());
            model.addAttribute("course",
                    new Course(courseName, CourseType.IPA, courseDescription, 1));
            model.addAttribute("courseId", id);
            return "course/updateCourse";
        }
    }

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
        Course course = courseService.getCourseById(courseId);
        List<CourseInformation> courseInformations =
                courseInformationService.getCourseInformationByCourse(course);
        model.addAttribute("course", course);
        model.addAttribute("courseInformations", courseInformations);
        return "course/readCourseById";
    }

    @GetMapping(value = "/new-information/{courseId}")
    public String createInformation(@PathVariable int courseId, Model model) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseInformation", new CourseInformation());
        return "course/createInformation";
    }

    @PostMapping(value = "/new-information/{courseId}")
    public String createInformation(@ModelAttribute CourseInformation courseInformation,
                                    @PathVariable int courseId,
                                    Model model) {
        Course course = courseService.getCourseById(courseId);
        courseInformation.setCourse(course);
        courseInformationService.createCourseInformation(courseInformation);
        return "redirect:/course/" + courseId;
    }

    @GetMapping(value = "/delete/{courseId}")
    public String deleteCourse(@PathVariable int courseId) {
        courseService.deleteCourseById(courseId);
        return "redirect:/course";
    }
}
