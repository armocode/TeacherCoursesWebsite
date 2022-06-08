package com.demo.udema.service;

import com.demo.udema.entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> findAllByCategoryId(int id);
    List<Course> findAll();
    List<Course> findAllByTitle(String title);
    Course findByTitle(String title);

    void save(Course course);
    void deleteByTitle(String title);
}
