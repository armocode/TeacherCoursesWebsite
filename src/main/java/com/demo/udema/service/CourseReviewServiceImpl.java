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

    @Override
    public List<CourseReviews> findAllSortByAnyTime(String teacherUsername) {
        return courseReviewRepository.findAllSortByAnyTime(teacherUsername);
    }

    @Override
    public List<CourseReviews> findAllSortByLatest(String teacherUsername) {
        return courseReviewRepository.findAllSortByLatest(teacherUsername);
    }

    @Override
    public List<CourseReviews> findReportedReviewsByTeacher() {
        return courseReviewRepository.findReportedReviewsByTeacher();
    }

    @Override
    public void deleteCourseReviewById(int id) {
        courseReviewRepository.deleteCourseReviewById(id);
    }

    @Override
    public void save(CourseReviews courseReviews) {
        courseReviewRepository.save(courseReviews);
    }

    @Override
    public void updateCourseReviewToFalse(int id) {
        courseReviewRepository.updateCourseReviewToFalse(id);
    }

    @Override
    public void updateCourseReviewToTrue(int id) {
        courseReviewRepository.updateCourseReviewToTrue(id);
    }
}
