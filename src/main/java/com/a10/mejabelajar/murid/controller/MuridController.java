package com.a10.mejabelajar.murid.controller;

import com.a10.mejabelajar.auth.model.Student;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.repository.CourseRepository;
import com.a10.mejabelajar.course.service.CourseService;
import com.a10.mejabelajar.murid.service.MuridService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Student> registrationMurid(@RequestBody Student student, @AuthenticationPrincipal User user) {
        var newStudent = studentService.getStudentByUser(user);
        return ResponseEntity.ok(muridService.regisMurid(newStudent));
    }

    @PostMapping(path = "/{id}", produces = {"application/json"})
    public String updateMurid(@PathVariable(value = "id") int id, @AuthenticationPrincipal User user) {
        var newStudent = studentService.getStudentByUser(user);
        muridService.updateMurid(id, newStudent);
        return "redirect:/course/" + id;
    }

    /**
     * Show all course.
     */
    @GetMapping(value = "")
    public String readCourse(@AuthenticationPrincipal User user, Model model) {
        List<Course> courses = courseService.getCourseByArchived(false);

        var newStudent = studentService.getStudentByUser(user);
        List<Course> courseTaken = newStudent.getNewCourse();
        List<Course> differences = new ArrayList<>(courses);
        differences.removeAll(courseTaken);
        model.addAttribute("courses", differences);
        return "murid/enrollCourse";
    }
}