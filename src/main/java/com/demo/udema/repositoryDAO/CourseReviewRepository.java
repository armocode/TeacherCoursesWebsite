package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.CourseReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

    @Query(value = "SELECT * FROM course_reviews" +
            " WHERE course_reviews.course_details_id IN" +
            " (SELECT id FROM course_details" +
            " WHERE course_details.course_id IN" +
            "(SELECT id FROM courses" +
            " WHERE courses.teacher_id IN" +
            " (SELECT id FROM users" +
            " WHERE users.username LIKE ?1)))" +
            "ORDER BY course_reviews.data ", nativeQuery = true)
    List<CourseReviews> findAllSortByAnyTime(String teacherUsername);

    @Query(value = "SELECT course_reviews.id FROM course_reviews" +
            " WHERE course_reviews.course_details_id IN" +
            " (SELECT id FROM course_details" +
            " WHERE course_details.course_id IN" +
            "(SELECT id FROM courses" +
            " WHERE courses.teacher_id IN" +
            " (SELECT id FROM users" +
            " WHERE users.username LIKE ?1)))", nativeQuery = true)
    Integer findCourseReviewIdByUsername(String teacherUsername);

    @Query(value = "SELECT * FROM course_reviews" +
            " WHERE course_reviews.course_details_id IN" +
            " (SELECT id FROM course_details" +
            " WHERE course_details.course_id IN" +
            "(SELECT id FROM courses" +
            " WHERE courses.teacher_id IN" +
            " (SELECT id FROM users" +
            " WHERE users.username LIKE ?1)))" +
            "ORDER BY course_reviews.data DESC", nativeQuery = true)
    List<CourseReviews> findAllSortByLatest(String teacherUsername);

    @Query(value = "SELECT * FROM course_reviews" +
            " WHERE is_reported = true", nativeQuery = true)
    List<CourseReviews> findReportedReviewsByTeacher();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM course_reviews" +
            " WHERE id = ?1", nativeQuery = true)
    void deleteCourseReviewById(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE course_reviews" +
            " SET is_reported = false" +
            " WHERE id = ?1", nativeQuery = true)
    void updateCourseReviewToFalse(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE course_reviews" +
            " SET is_reported = true" +
            " WHERE id = ?1", nativeQuery = true)
    void updateCourseReviewToTrue(int id);

    @Query(value = "SELECT id FROM course_reviews" +
            " WHERE course_details_id IN" +
            "(SELECT id FROM course_details" +
            " WHERE course_id IN" +
            " (SELECT id FROM courses" +
            "  WHERE teacher_id IN" +
            "   (SELECT id FROM users" +
            "    WHERE users.username LIKE ?1)))", nativeQuery = true)
    List<Integer> checkReviewsIdByTeacherUsername(String username);

    @Query(value = "SELECT id FROM course_reviews" +
            " WHERE course_reviews.user_id IN" +
            "(SELECT id FROM users" +
            " WHERE username LIKE ?1)", nativeQuery = true)
    Collection<Integer> findCourseReviewIdByStudentUsername(String username);

    @Query(value = "SELECT id FROM course_reviews" +
            " WHERE course_reviews.course_details_id IN" +
            "(SELECT id FROM course_details" +
            " WHERE course_details.course_id IN" +
            " (SELECT id FROM courses " +
            "  WHERE courses.title LIKE ?1))", nativeQuery = true)
    Collection<Integer> findCourseReviewIdByCourseTitle(String title);
}