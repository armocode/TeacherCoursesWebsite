package com.demo.udema.repository;

import com.demo.udema.entity.Course;
import com.demo.udema.entity.CourseReviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseReviewRepository extends JpaRepository<CourseReviews, Integer> {
    List<CourseReviews> findAll();
}
