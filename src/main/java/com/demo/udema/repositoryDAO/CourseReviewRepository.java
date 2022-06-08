package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.CourseReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseReviewRepository extends JpaRepository<CourseReviews, Double> {

    @Query(value = "SELECT * FROM course_reviews " +
            "JOIN course_details ON course_details.id = course_reviews.course_details_id " +
            "WHERE course_details.course_id IN" +
            "(SELECT courses.id FROM Users " +
            "JOIN orders ON users.id = user_id " +
            "JOIN courses ON courses.id = course_id " +
            "WHERE courses.title LIKE ?1)", nativeQuery = true)
    List<CourseReviews> findAllByTitle(String title);


    @Query(value = "SELECT CAST(AVG(course_reviews.rating) AS DECIMAL(10,2)) FROM courses" +
            " JOIN course_details ON course_details.course_id = courses.id" +
            " JOIN course_reviews ON course_details.id = course_reviews.course_details_id" +
            " WHERE courses.title LIKE ?1", nativeQuery = true)
    Double findRatingByTitle(String title);

    @Query(value = "SELECT COUNT(course_reviews.rating) FROM courses" +
            " JOIN course_details ON course_details.course_id = courses.id" +
            " JOIN course_reviews ON course_details.id = course_reviews.course_details_id" +
            " WHERE courses.title LIKE ?1", nativeQuery = true)
    Integer countRatingByTitle(String title);

    @Query(value= "SELECT * FROM course_reviews" +
            " JOIN Users ON course_reviews.user_id = users.id" +
            " JOIN orders ON users.id = orders.user_id " +
            " JOIN courses ON courses.id = orders.course_id " +
            " GROUP BY course_reviews.data" +
            " ORDER BY course_reviews.data ", nativeQuery = true)
    List<CourseReviews> findAllSortByAnyTime();

    @Query(value= "SELECT * FROM course_reviews" +
            " JOIN Users ON course_reviews.user_id = users.id" +
            " JOIN orders ON users.id = orders.user_id " +
            " JOIN courses ON courses.id = orders.course_id " +
            " GROUP BY course_reviews.data" +
            " ORDER BY course_reviews.data DESC", nativeQuery = true)
    List<CourseReviews> findAllSortByLatest();
}
