package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.CourseReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseReviewRepository extends JpaRepository<CourseReviews, Integer> {

    @Query(value = "SELECT * FROM Users" +
            " JOIN orders ON users.id = user_id" +
            " JOIN courses ON courses.id = course_id" +
            " JOIN course_reviews ON course_reviews.user_id = users.id" +
            " WHERE courses.title = ?1", nativeQuery = true)
    CourseReviews findByTitle(String title);
//    List<CourseReviews> findAll(String title);
}
