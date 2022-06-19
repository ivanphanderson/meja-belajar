package com.a10.mejabelajar.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.a10.mejabelajar.course.exception.CourseInformationInvalidException;
import com.a10.mejabelajar.course.model.CourseInformation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;



class CourseInformationValidatorTest {

    @Test
    void testConstructorIsPrivate() throws NoSuchMethodException {
        Constructor<CourseInformationValidator> constructor =
                CourseInformationValidator.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, () -> {
            constructor.newInstance();
        });
    }

    @Test
    void testCourseInformationValidatorIfValid() {
        CourseInformation courseInformation = new CourseInformation();
        courseInformation.setCourseInformationTitle("Title");

        CourseInformationValidator.validateCourseInformation(courseInformation);
    }

    @Test
    void testCourseInformationValidatorIfTitleIsEmpty() {
        CourseInformation courseInformation = new CourseInformation();
        courseInformation.setCourseInformationTitle("");

        Exception exception = assertThrows(CourseInformationInvalidException.class, () -> {
            CourseInformationValidator.validateCourseInformation(courseInformation);
        });

        String expectedMessage = "Required Title";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
