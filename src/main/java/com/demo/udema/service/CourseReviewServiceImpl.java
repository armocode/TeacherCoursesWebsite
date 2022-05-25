package com.demo.udema.service;

import com.demo.udema.entity.CourseReviews;
import com.demo.udema.repository.CourseReviewRepository;

import java.util.List;

public class CourseReviewServiceImpl implements CourseReviewService{

    public CourseReviewRepository courseReviewRepository;

    @Override
    public List<CourseReviews> findAll() {
        return courseReviewRepository.findAll();
    }
}
