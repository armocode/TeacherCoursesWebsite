package com.demo.udema.service;

import com.demo.udema.entity.Course;
import com.demo.udema.repository.CourseRepository;
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

    @Override
    public Course findByTitle(String title) {
        //        Optional<Course> result =courseRepository.findAllByTitle(title);
//        Course course = null;
//        if(result.isPresent()){
//course =result.get();
//        }
        return null;
    }

    @Override
    public void deleteByTitle(String title) {
        courseRepository.deleteByTitle(title);
    }
}
