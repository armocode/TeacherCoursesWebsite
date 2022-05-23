package com.demo.udema.service;

import com.demo.udema.entity.Course;

public interface CourseService {
    void save(Course course);
    Course findByTitle(String title);
}
