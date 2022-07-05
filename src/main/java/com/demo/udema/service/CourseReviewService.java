package com.demo.udema.service;

import com.demo.udema.entity.CourseReviews;

import java.util.List;

public interface CourseReviewService {
    List<CourseReviews> findAllByTitle(String title);
    Double findRatingByTitle(String title);
    Integer countRatingByTitle(String title);
    List<CourseReviews> findAllSortByAnyTime(String teacherUsername);
    List<CourseReviews> findAllSortByLatest(String teacherUsername);
    List<CourseReviews> findReportedReviewsByTeacher();
    void deleteCourseReviewById(int id);
    void save(CourseReviews courseReviews);
    void updateCourseReviewToFalse(int id);
    void updateCourseReviewToTrue(int id);
    List<Integer> checkReviewsIdByTeacherUsername(String username);
    Integer findCourseReviewIdByStudentUsername(String username);
    List<Integer> findCourseReviewIdByCourseTitle(String title);




}
