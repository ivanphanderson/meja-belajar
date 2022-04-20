package com.a10.mejabelajar.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/course")
public class CourseController {

    @RequestMapping(method = RequestMethod.GET, path = "")
    public String createCourse() {
        return "course/createCourse";
    }
}
