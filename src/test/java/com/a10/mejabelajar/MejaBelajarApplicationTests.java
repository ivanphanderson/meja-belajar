package com.a10.mejabelajar;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.a10.mejabelajar.course.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MejaBelajarApplicationTests {

    @Autowired
    CourseService courseService;

    @Test
    void contextLoads() {
        MejaBelajarApplication.main(new String[0]);
        assertNotNull(courseService);
    }

}
