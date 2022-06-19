package com.demo.udema.service;

import com.demo.udema.entity.Course;
import com.demo.udema.entity.CourseReviews;
import com.demo.udema.repositoryDAO.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    public CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAllByCategoryId(int id) {
        return  courseRepository.findAllByCategoryId(id);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public void save(Course course) {
    courseRepository.save(course);
    }


    public List<Course> findAllByTitle(String title) {
        return courseRepository.findAllByTitle(title);
    }

    @Override
    public Course findByTitle(String title) {
        return courseRepository.findByTitle(title);
    }

    @Override
    public Course findById(int id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> findAllTeacherCourseByUsername(String username) {
        return courseRepository.findAllTeacherCourseByUsername(username);
    }


    @Override
    public void deleteByTitle(String title) {
        courseRepository.deleteByTitle(title);
    }

    @Override
    public List<Course> findAllSortByAnyTime() {
        return courseRepository.findAllSortByAnyTime();
    }
}
