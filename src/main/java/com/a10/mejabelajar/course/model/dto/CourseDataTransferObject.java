package com.a10.mejabelajar.course.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDataTransferObject {
    private String courseName;
    private String courseType;
    private String courseDescription;
    private String courseDuration;
}
