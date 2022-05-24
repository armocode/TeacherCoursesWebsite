package com.demo.udema.repository;

import com.demo.udema.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByTitle(String title);
    List<Course> findAll();
    void deleteByTitle(String title);

}
