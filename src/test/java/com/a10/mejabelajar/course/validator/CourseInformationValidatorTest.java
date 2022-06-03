package com.a10.mejabelajar.course.validator;

import com.a10.mejabelajar.course.exception.CourseInformationInvalidException;
import com.a10.mejabelajar.course.model.CourseInformation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class CourseInformationValidatorTest {

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<CourseInformationValidator> constructor = CourseInformationValidator.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, () -> {
            constructor.newInstance();
        });
    }

    @Test
    public void testCourseInformationValidatorIfValid() {
        CourseInformation courseInformation = new CourseInformation();
        courseInformation.setCourseInformationTitle("Title");

        CourseInformationValidator.validateCourseInformation(courseInformation);
    }

    @Test
    public void testCourseInformationValidatorIfTitleIsEmpty() {
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
