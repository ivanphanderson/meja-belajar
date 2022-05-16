package com.a10.mejabelajar.course.validator;

import com.a10.mejabelajar.course.exception.CourseInvalidException;
import com.a10.mejabelajar.course.model.CourseType;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CourseValidator {

    /**
     * Validate all important course attributes.
     */
    public static void validateCourseAttribute(CourseDataTransferObject courseDataTransferObject) {
        if (!validateCourseName(courseDataTransferObject.getCourseName())) {
            throw new CourseInvalidException("Course name should not be empty");
        }
        if (!validateCourseType(courseDataTransferObject.getCourseType())) {
            throw new CourseInvalidException("Choose valid course type!");
        }
        if (!validateCourseDuration(courseDataTransferObject.getCourseDuration())) {
            throw new CourseInvalidException("Duration should be a positive Integer");
        }
    }

    private static boolean validateCourseName(String courseName) {
        return (!courseName.equals(""));
    }

    private static boolean validateCourseType(String userCourseType) {
        try {
            CourseType.valueOf(userCourseType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static boolean validateCourseDuration(String duration) {
        try {
            var durationDouble = Double.parseDouble(duration);
            return durationDouble > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
