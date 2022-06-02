package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.CourseReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseReviewRepository extends JpaRepository<CourseReviews, Double> {

    @Query(value = "SELECT * FROM Users" +
        " JOIN orders ON users.id = user_id" +
        " JOIN courses ON courses.id = course_id" +
        " JOIN course_reviews ON course_reviews.user_id = users.id" +
        " WHERE courses.title = ?1 GROUP BY orders.user_id", nativeQuery = true)
    List<CourseReviews> findAllByTitle(String title); // orders.user_id = Many

    @Query(value = "SELECT CAST(AVG(course_reviews.rating) AS DECIMAL(10,2)) FROM courses" +
            " JOIN course_details ON course_details.course_id = courses.id" +
            " JOIN course_reviews ON course_details.id = course_reviews.course_details_id" +
            " WHERE courses.title LIKE ?1", nativeQuery = true)
    double findRatingByTitle(String title);

}
