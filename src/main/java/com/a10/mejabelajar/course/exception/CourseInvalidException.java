package com.a10.mejabelajar.course.exception;

public class CourseInvalidException extends RuntimeException {
    public CourseInvalidException(String errorMessage) {
        super(errorMessage);
    }
}
