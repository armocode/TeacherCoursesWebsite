package com.demo.udema.repository;

import com.demo.udema.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findByTitle(String title);
}
