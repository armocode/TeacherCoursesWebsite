package com.demo.udema.service;

import com.demo.udema.entity.Course;
import com.demo.udema.entity.CourseReviews;

import java.util.List;

public interface CourseService {
    List<Course> findAllByCategoryId(int id);
    List<Course> findAll();
    List<Course> findAllByTitle(String title);
    Course findByTitle(String title);
    Course findById(int id);
    List<Course> findAllTeacherCourseByUsername(String username);
    void save(Course course);
    void deleteById(int id);

}
