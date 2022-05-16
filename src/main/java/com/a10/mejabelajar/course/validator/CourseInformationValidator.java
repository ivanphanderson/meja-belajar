package com.a10.mejabelajar.course.validator;

import com.a10.mejabelajar.course.exception.CourseInformationInvalidException;
import com.a10.mejabelajar.course.model.CourseInformation;

public class CourseInformationValidator {

    /**
     * Validate course information title.
     */
    public static void validateCourseInformation(CourseInformation courseInformation) {
        if (!validateCourseInformationTitle(courseInformation.getCourseInformationTitle())) {
            throw new CourseInformationInvalidException("Required Title");
        }
    }

    private static boolean validateCourseInformationTitle(String title) {
        return !title.equals("");
    }
}
