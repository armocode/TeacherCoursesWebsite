package com.demo.udema.service;

import com.demo.udema.entity.CourseReviews;

import java.util.List;

public interface CourseReviewService {
//    CourseReviews findByTitle(String title);
    List<CourseReviews> findAllByTitle(String title);
    double findRatingByTitle(String title);
}
