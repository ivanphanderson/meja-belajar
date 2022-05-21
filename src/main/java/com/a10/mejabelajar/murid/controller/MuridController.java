package com.a10.mejabelajar.murid.controller;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.course.service.CourseService;
import com.a10.mejabelajar.murid.service.MuridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/murid")
public class MuridController {

    @Autowired
    MuridService muridService;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    @PostMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity registrationMurid(@RequestBody Student student, @AuthenticationPrincipal User user) {
        Student newStudent = studentService.getStudentByUser(user);
        return ResponseEntity.ok(muridService.regisMurid(newStudent));
    }

    @PostMapping(path = "/{id}", produces = {"application/json"})
    public String updateMurid(@PathVariable(value = "id") int id, @AuthenticationPrincipal User user) {
        Student student = studentService.getStudentByUser(user);
        muridService.updateMurid(id, student);
        return "redirect:/course/" + id;
    }

    /**
     * Show all course.
     */
    @GetMapping(value = "")
    public String readCourse(@AuthenticationPrincipal User user, Model model) {
        List<Course> courses = courseService.getCourseByArchived(false);

        Student student = studentService.getStudentByUser(user);
        List<Course> courseTaken = student.getNewCourse();
        List<Course> differences = new ArrayList<>(courses);
        differences.removeAll(courseTaken);
        model.addAttribute("courses", differences);
        return "murid/enrollCourse";
    }
}