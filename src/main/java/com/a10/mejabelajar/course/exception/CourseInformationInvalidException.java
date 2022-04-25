package com.a10.mejabelajar.course.exception;

public class CourseInformationInvalidException extends RuntimeException {
    public CourseInformationInvalidException(String errorMessage) {
        super(errorMessage);
    }
}
