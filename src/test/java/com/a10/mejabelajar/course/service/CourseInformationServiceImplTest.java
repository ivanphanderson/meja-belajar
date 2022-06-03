package com.a10.mejabelajar.course.service;

import com.a10.mejabelajar.course.model.Course;
import com.a10.mejabelajar.course.model.CourseInformation;
import com.a10.mejabelajar.course.model.CourseNotification;
import com.a10.mejabelajar.course.repository.CourseInformationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseInformationServiceImplTest {

    @Mock
    CourseInformationRepository courseInformationRepository;

    @InjectMocks
    @Spy
    CourseInformationServiceImpl courseInformationService;

    private CourseInformation courseInformation;
    private Course course;

    private static final int COURSE_INFORMATION_ID = 109;

    @BeforeEach
    public void setUp() {
        course = new Course();
        courseInformation = new CourseInformation();
        courseInformation.setCourse(course);
        courseInformation.setCourseInformationTitle("TITLE");
        courseInformation.setCourseInformationBody("BODY");
        courseInformation.setCreatedAt(new Date());
        courseInformation.setUpdatedAt(new Date());
        courseInformation.setId(COURSE_INFORMATION_ID);
    }

    @Test
    public void testCreateCourseInformation() {
        lenient().when(courseInformationService.createCourseInformation(courseInformation)).thenReturn(courseInformation);
    }

    @Test
    public void testUpdateCourseInformation() {
        courseInformationService.createCourseInformation(courseInformation);
        String pastTitle = courseInformation.getCourseInformationTitle();
        String oldBody = courseInformation.getCourseInformationBody();
        Date oldUpdatedAt = courseInformation.getUpdatedAt();
        List<CourseNotification> oldCourseNotifications = courseInformation.getCourseNotifications();

        String newTitle = "NewTitle";
        courseInformation.setCourseInformationTitle(newTitle);

        String newBody = "New BOdyy";
        courseInformation.setCourseInformationBody(newBody);

        doReturn(courseInformation).when(courseInformationService).getCourseInformationById(COURSE_INFORMATION_ID);
        lenient().when(courseInformationService.updateCourseInformation(COURSE_INFORMATION_ID, courseInformation)).thenReturn(courseInformation);
        CourseInformation courseInformationResult = courseInformationService.updateCourseInformation(COURSE_INFORMATION_ID, courseInformation);

        assertEquals(courseInformationResult.getId(), courseInformation.getId());
        assertEquals(courseInformationResult.getCourseNotifications(), oldCourseNotifications);
        assertNotEquals(courseInformationResult.getCourseInformationTitle(), pastTitle);
        assertNotEquals(courseInformationResult.getCourseInformationBody(), oldBody);
        assertNotEquals(courseInformationResult.getUpdatedAt(), oldUpdatedAt);
    }

    @Test
    public void testDeleteCourseInformationById() {
        courseInformationService.createCourseInformation(courseInformation);
        lenient().when(courseInformationRepository.findById(COURSE_INFORMATION_ID)).thenReturn(courseInformation);
        courseInformationService.deleteCourseInformationById(COURSE_INFORMATION_ID);
        lenient().when(courseInformationService.getCourseInformationById(COURSE_INFORMATION_ID)).thenReturn(null);
        assertNull(courseInformationService.getCourseInformationById(COURSE_INFORMATION_ID));
    }

    @Test
    public void testGetCourseInformationById() {
        lenient().when(courseInformationService.getCourseInformationById(COURSE_INFORMATION_ID)).thenReturn(courseInformation);
        CourseInformation calledCourseInformation = courseInformationService.getCourseInformationById(COURSE_INFORMATION_ID);
        assertEquals(calledCourseInformation.getId(), courseInformation.getId());
    }
}
