package com.a10.mejabelajar.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.a10.mejabelajar.course.exception.CourseInvalidException;
import com.a10.mejabelajar.course.model.dto.CourseDataTransferObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class CourseValidatorTest {

    private CourseDataTransferObject courseDataTransferObject;

    /**
     * Run this before run every single test.
     */
    @BeforeEach
    void setUp() {
        courseDataTransferObject = new CourseDataTransferObject();
        courseDataTransferObject.setCourseName("Good Course");
        courseDataTransferObject.setCourseType("IPS");
        courseDataTransferObject.setCourseDuration("100");
        courseDataTransferObject.setCourseDescription("HELLOOO");
    }

    @Test
    void testConstructorIsPrivate() throws NoSuchMethodException {
        Constructor<CourseValidator> constructor = CourseValidator.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, () -> {
            constructor.newInstance();
        });
    }

    @Test
    void testValidateCourseNameIfInvalid() {
        courseDataTransferObject.setCourseName("");
        Exception exception = assertThrows(CourseInvalidException.class, () -> {
            CourseValidator.validateCourseAttribute(courseDataTransferObject);
        });

        String expectedMessage = "Course name should not be empty";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testValidateCourseTypeIfInvalid() {
        courseDataTransferObject.setCourseType("asdasa");
        Exception exception = assertThrows(CourseInvalidException.class, () -> {
            CourseValidator.validateCourseAttribute(courseDataTransferObject);
        });

        String expectedMessage = "Choose valid course type!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testValidateCourseDurationIfDurationIsNegative() {
        courseDataTransferObject.setCourseDuration("-99");
        Exception exception = assertThrows(CourseInvalidException.class, () -> {
            CourseValidator.validateCourseAttribute(courseDataTransferObject);
        });

        String expectedMessage = "Duration should be a positive Integer";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testValidateCourseDurationIfDurationIsNotInteger() {
        courseDataTransferObject.setCourseDuration("12noos");
        Exception exception = assertThrows(CourseInvalidException.class, () -> {
            CourseValidator.validateCourseAttribute(courseDataTransferObject);
        });

        String expectedMessage = "Duration should be a positive Integer";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testValidateCourseIfValid() {
        CourseValidator.validateCourseAttribute(courseDataTransferObject);
    }
}
