package com.a10.mejabelajar.course.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.model.CourseNotification;
import com.a10.mejabelajar.course.repository.CourseInformationRepository;
import com.a10.mejabelajar.course.repository.CourseNotificationRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CourseInformationServiceImplTest {

    @Mock
    CourseInformationRepository courseInformationRepository;

    @Mock
    CourseNotificationRepository courseNotificationRepository;

    @InjectMocks
    @Spy
    CourseInformationServiceImpl courseInformationService;

    private CourseInformation courseInformation;
    private Course course;
    private CourseNotification courseNotification;

    private static final int COURSE_INFORMATION_ID = 109;

    /**
     * Run this before run every single test.
     */
    @BeforeEach
    void setUp() {
        course = new Course();
        courseNotification = new CourseNotification();
        courseInformation = new CourseInformation();
        courseInformation.setCourse(course);
        courseInformation.setCourseInformationTitle("TITLE");
        courseInformation.setCourseInformationBody("BODY");
        courseInformation.setCreatedAt(new Date());
        courseInformation.setUpdatedAt(new Date());
        courseInformation.setId(COURSE_INFORMATION_ID);
    }

    @Test
    void testCreateCourseInformation() {
        lenient().when(courseInformationService.createCourseInformation(courseInformation))
                .thenReturn(courseInformation);
        assertNotNull(courseInformationService.createCourseInformation(courseInformation));
        verify(courseInformationRepository, times(1)).save(any(CourseInformation.class));
    }

    @Test
    void testUpdateCourseInformation() {
        courseInformationService.createCourseInformation(courseInformation);
        final String pastTitle = courseInformation.getCourseInformationTitle();
        final String oldBody = courseInformation.getCourseInformationBody();
        final Date oldUpdatedAt = courseInformation.getUpdatedAt();
        final List<CourseNotification> oldCourseNotifications =
                courseInformation.getCourseNotifications();

        String newTitle = "NewTitle";
        courseInformation.setCourseInformationTitle(newTitle);

        String newBody = "New BOdyy";
        courseInformation.setCourseInformationBody(newBody);

        doReturn(courseInformation).when(courseInformationService)
                .getCourseInformationById(COURSE_INFORMATION_ID);
        lenient().when(courseInformationService
                .updateCourseInformation(COURSE_INFORMATION_ID, courseInformation))
                .thenReturn(courseInformation);
        CourseInformation courseInformationResult =
                courseInformationService
                        .updateCourseInformation(COURSE_INFORMATION_ID, courseInformation);

        assertEquals(courseInformation.getId(), courseInformationResult.getId());
        assertEquals(oldCourseNotifications, courseInformationResult.getCourseNotifications());
        assertNotEquals(pastTitle, courseInformationResult.getCourseInformationTitle());
        assertNotEquals(oldBody, courseInformationResult.getCourseInformationBody());
        assertNotEquals(oldUpdatedAt, courseInformationResult.getUpdatedAt());
        verify(courseInformationRepository, times(2)).save(courseInformationResult);
    }

    @Test
    void testDeleteCourseInformationById() {
        courseInformationService.createCourseInformation(courseInformation);
        List<CourseNotification> courseNotifications = new ArrayList<>();
        courseNotifications.add(courseNotification);

        lenient().when(courseInformationRepository
                .findById(COURSE_INFORMATION_ID)).thenReturn(courseInformation);
        lenient().when(courseNotificationRepository.findAllByCourseInformation(courseInformation))
                .thenReturn(courseNotifications);
        courseInformationService.deleteCourseInformationById(COURSE_INFORMATION_ID);
        lenient().when(courseInformationService
                .getCourseInformationById(COURSE_INFORMATION_ID)).thenReturn(null);

        assertNull(courseInformationService.getCourseInformationById(COURSE_INFORMATION_ID));
        verify(courseInformationRepository, times(1)).delete(courseInformation);
    }

    @Test
    void testGetCourseInformationById() {
        lenient().when(courseInformationService
                .getCourseInformationById(COURSE_INFORMATION_ID)).thenReturn(courseInformation);
        CourseInformation calledCourseInformation =
                courseInformationService.getCourseInformationById(COURSE_INFORMATION_ID);
        assertEquals(courseInformation.getId(), calledCourseInformation.getId());
        verify(courseInformationRepository, times(1)).findById(COURSE_INFORMATION_ID);
    }
}
