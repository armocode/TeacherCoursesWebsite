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
    public List<CourseReviews> findAllByTitle(String title) {
        return courseReviewRepository.findAllByTitle(title);
    }

    @Override
    public Double findRatingByTitle(String title) {
        return courseReviewRepository.findRatingByTitle(title);
    }

    @Override
    public Integer countRatingByTitle(String title) {
        return courseReviewRepository.countRatingByTitle(title);
    }
}
