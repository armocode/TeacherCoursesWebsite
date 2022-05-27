package com.demo.udema.service;

import com.demo.udema.entity.CourseReviews;
import com.demo.udema.repository.CourseReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseReviewServiceImpl implements CourseReviewService{

    public CourseReviewRepository courseReviewRepository;

    public CourseReviewServiceImpl(CourseReviewRepository courseReviewRepository) {
        this.courseReviewRepository = courseReviewRepository;
    }

    @Override
    public List<CourseReviews> findAll() {
        return courseReviewRepository.findAll();
    }
}
