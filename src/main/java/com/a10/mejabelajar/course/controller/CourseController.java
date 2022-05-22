package com.a10.mejabelajar.course.controller;

import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.Teacher;
import com.a10.mejabelajar.auth.model.User;
import com.a10.mejabelajar.auth.service.StudentService;
import com.a10.mejabelajar.auth.service.TeacherService;
import com.a10.mejabelajar.course.exception.CourseInvalidException;
import com.a10.mejabelajar.course.model.*;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import com.a10.mejabelajar.course.service.CourseNotificationService;
import com.a10.mejabelajar.course.service.CourseService;
import com.a10.mejabelajar.murid.model.Rate;
import com.a10.mejabelajar.murid.service.RateService;
import java.sql.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RateService rateService;

    @Autowired
    private CourseNotificationService courseNotificationService;

    private static final String COURSE = "course";
    private static final String COURSE_ID = "courseId";
    private static final String COURSE_TYPES = "courseTypes";
    private static final String ERROR = "error";
    private static final String STUDENT = "student";
    private static final String TEACHER = "teacher";
    private static final String REDIRECT_COURSE = "redirect:/course/";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    public static final long HOUR = 3600L * 1000; // in milli-seconds.

    @PostMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    /**
     * Show create course page.
     */
    @GetMapping(path = "/create")
    public String createCourse(Model model) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return REDIRECT_LOGIN;
        }
        var user = (User) principal;

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
            @ModelAttribute("courseDto") CourseDataTransferObject courseDataTransferObject,
            Model model) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return REDIRECT_LOGIN;
        }
        var user = (User) principal;

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
            @PathVariable int id, Model model) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return REDIRECT_LOGIN;
        }
        var user = (User) principal;

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
            @ModelAttribute CourseDataTransferObject courseDataTransferObject,
            @PathVariable int id,
            Model model) {

        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return REDIRECT_LOGIN;
        }
        var user = (User) principal;

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

    /**
     * Show all course.
     */
    @GetMapping(value = "")
    public String readCourse(
            @RequestParam(name = "error", required = false) String error,
            Model model) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return REDIRECT_LOGIN;
        }
        var user = (User) principal;

        List<Course> courses = courseService.   getCourses();
        if (error != null) {
            model.addAttribute(ERROR, error);
        }
        if (user.getRole() == Role.STUDENT) {
            model.addAttribute(STUDENT, STUDENT);
        } else if (user.getRole() == Role.TEACHER) {
            model.addAttribute(TEACHER, TEACHER);
        }
        model.addAttribute("courses", courses);
        return "course/readCourse";
    }

    /**
     * Show course page specified by its id in path variable.
     */
    @GetMapping(value = "/{courseId}")
    public String readCourseById(
            @PathVariable(value = "courseId") int courseId,
            @RequestParam(name = "error", required = false) String error,
            Model model) throws SQLException {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return REDIRECT_LOGIN;
        }
        var user = (User) principal;

        var course = courseService.getCourseById(courseId);

        // Add if student
        if (user.getRole() == Role.STUDENT) {
            var student = studentService.getStudentByUser(user);
            List<Course> courses = courseService.getCoursesByStudent(student);
            if (!courses.contains(course)) {
                return REDIRECT_COURSE + "?error=You are not enrolled to this course";
            }
            model.addAttribute(STUDENT, student);
        }
        if (user.getRole() == Role.TEACHER) {
            var teacher = teacherService.getTeacherByUser(user);
            String isValid = validateTeacherAccess(teacher, course, "Read The Course");
            if (!isValid.equals("")) {
                return isValid;
            }
            model.addAttribute(TEACHER, teacher);
        }


        List<CourseInformation> courseInformations = course.getCourseInformations();

        if (error != null) {
            model.addAttribute(ERROR, error);
        }
        model.addAttribute(COURSE, course);
        model.addAttribute("courseInformations", courseInformations);

        // Rate a course
        String studentId = user.getId();

        // Average rate
        Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-3-229-252-6.compute-1.amazonaws.com:5432/dek2gmuc9cqfl6", "snmncaqhruoukl", "5611939bd8f27a08fe9504b99386a08ea88243d455770f5984e07f4617234bc5");
        Statement rateStat = conn.createStatement();
        String formatString = String.format("SELECT AVG(nilai_rating) AS hasil FROM rate WHERE id_course=%s;", courseId);
        ResultSet averageRate = rateStat.executeQuery(formatString);
        averageRate.next();
        int newAverageRate = averageRate.getInt("hasil");

        Rate listRate = rateService.getByIdStudentAndIdCourse(studentId, courseId);
        model.addAttribute("rate", new Rate());
        model.addAttribute("idCourse", courseId);
        model.addAttribute("currentRate", listRate);
        model.addAttribute("finalRate", newAverageRate);
        return "course/readCourseById";
    }

    /**
     * Rate a course.
     */
    @PostMapping(value = "/rate")
    public String rateCourse(@RequestParam Integer rate, @RequestParam Integer idCourse) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String studentId = ((User)principal).getId();

        var newRate = rateService.getByIdStudentAndIdCourse(studentId, idCourse);
        if (newRate == null) {
            rateService.createRate(studentId, idCourse, rate);
            return "redirect:/murid";
        } else {
            return REDIRECT_COURSE + idCourse + "?error=Anda sudah memberikan rate";
        }

    }


    /**
     * Archive a course.
     */
    @PostMapping(value = "/archive/{courseId}")
    public String archiveCourse(@PathVariable int courseId) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return REDIRECT_LOGIN;
        }
        var user = (User) principal;

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
     * Show Notification.
     */
    @GetMapping(value = "/notification")
    public String courseNotification(
            Model model
    ) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return REDIRECT_LOGIN;
        }
        var user = (User) principal;

        if (user.getRole() == Role.STUDENT) {
            var student = studentService.getStudentByUser(user);

            var instant = Instant.now();
            var date = Date.from(instant);
            var newDate = new Date(date.getTime() + 7 * HOUR);

            List<CourseNotification> courseNotifications =
                    courseNotificationService
                            .getCourseNotificationByStudentAndCreatedAtIsGreaterThanEqual(
                                    student,
                                    student.getLastNotifBtnClick()
                            );

            List<CourseNotification> courseNotifications1 =
                    courseNotificationService
                            .getCourseNotificationByStudentAndCreatedAtIsLessThan(
                                    student,
                                    student.getLastNotifBtnClick()
                            );

            studentService.setStudentLastNotifBtnClick(student, newDate);
            model.addAttribute("courseNotifications", courseNotifications);
            model.addAttribute("courseNotifications1", courseNotifications1);
            return "course/courseNotification";
        } else {
            return REDIRECT_COURSE + "?error=The feature is only for student";
        }
    }

    /**
     * Validate teacher access to a course.
     */
    public String validateTeacherAccess(Teacher teacher, Course course, String action) {
        if (course.isArchived()
                && !action.equals("Read The Course")
                && course.getTeacher() == teacher) {
            return REDIRECT_COURSE + course.getId() + "?error=This course is archived";
        }
        if (course.getTeacher() != teacher) {
            return REDIRECT_COURSE + "?error=You don't have access to " + action;
        }
        return "";
    }
}
