package com.demo.udema.service;

import com.demo.udema.entity.Course;
import com.demo.udema.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Course findByTitle(String title) {
        return courseRepository.findByTitle(title);
    }
}
