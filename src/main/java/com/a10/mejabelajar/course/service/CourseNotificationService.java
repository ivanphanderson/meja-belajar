package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.course.model.CourseInformation;

public interface CourseNotificationService {
    void handleCreateInformation(CourseInformation courseInformation);

    void handleUpdateInformation(CourseInformation courseInformation);
}
