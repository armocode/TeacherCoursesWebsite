package com.demo.udema.service;

import com.demo.udema.entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> findAllByCategoryId(int id);
    List<Course> findAll();
    void save(Course course);
    List<Course> findAllByTitle(String title);

    Course findByTitle(String title);

    void deleteByTitle(String title);
}
