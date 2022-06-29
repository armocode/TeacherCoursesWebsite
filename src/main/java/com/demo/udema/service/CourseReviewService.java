package com.demo.udema.service;

import com.demo.udema.entity.CourseReviews;

import java.util.List;

public interface CourseReviewService {
//    CourseReviews findByTitle(String title);
    List<CourseReviews> findAllByTitle(String title);
    Double findRatingByTitle(String title);
    Integer countRatingByTitle(String title);

    List<CourseReviews> findAllSortByAnyTime(String teacherUsername);
    List<CourseReviews> findAllSortByLatest(String teacherUsername);
    List<CourseReviews> findReportedReviewsByTeacher();
    void deleteCourseReviewById(int id);
    void modifyCourseReviewById(boolean isReported, int id);
    void save(CourseReviews courseReviews);


}
