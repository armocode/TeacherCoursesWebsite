package com.demo.udema.service;

import com.demo.udema.entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> findAll();
    void save(Course course);
    Course findByTitle(String title);

    void deleteByTitle(String title);
}
