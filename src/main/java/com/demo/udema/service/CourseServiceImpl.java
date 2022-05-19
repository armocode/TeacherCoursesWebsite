package com.demo.udema.service;

import com.demo.udema.entity.Course;
import com.demo.udema.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void save(Course course) {
        course.setTitle(course.getTitle());
        course.setCategory(course.getCategory());
        course.setPrice(course.getPrice());
        courseRepository.save(course);
    }

    @Override
    public Course findCourseByTitle(String title) {
        return courseRepository.findCourseByTitle(title);
    }
}
