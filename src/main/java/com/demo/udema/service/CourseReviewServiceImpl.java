package com.demo.udema.service;

import com.demo.udema.entity.CourseReviews;
import com.demo.udema.repositoryDAO.CourseReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseReviewServiceImpl implements CourseReviewService {

    public CourseReviewRepository courseReviewRepository;

    public CourseReviewServiceImpl(CourseReviewRepository courseReviewRepository) {
        this.courseReviewRepository = courseReviewRepository;
    }

    @Override
    public CourseReviews findByTitle(String title) {
        return courseReviewRepository.findByTitle(title);
    }

    @Override
    public List<CourseReviews> findAllByTitle(String title) {
        return null;
    }
}
