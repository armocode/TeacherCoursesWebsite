package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.Course;
import com.demo.udema.entity.CourseDetails;
import com.demo.udema.entity.CourseReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByCategoryId(int id);
    List<Course> findAll();
    void deleteByTitle(String title);

    //Neiseina surikiuoti pagal list number, neuzpildyti puslapiai sugriuna.
//    @Query(value = "SELECT * FROM courses" +
//            " JOIN course_details ON courses.id = course_details.course_id" +
//            " JOIN lesson_topics ON course_details.id = lesson_topics.course_details_id" +
//            " WHERE courses.title LIKE ?1" +
//            " ORDER BY lesson_topics.list_number DESC", nativeQuery = true)
    Course findByTitle(String title);

    Course findById(int id);

    @Query(value = "SELECT * FROM courses" +
            " WHERE courses.title LIKE %?1%" +
            " GROUP BY courses.title", nativeQuery = true)
    List<Course> findAllByTitle(String title);

    @Query(value= "SELECT * FROM courses " +
            " JOIN course_details ON course_details.course_id = courses.id" +
            " JOIN course_reviews ON course_details.id = course_reviews.course_details_id" +
            " GROUP BY course_reviews.id" +
            " ORDER BY course_reviews.data", nativeQuery = true)
    List<Course> findAllSortByAnyTime();

    @Query(value = "SELECT * FROM courses" +
            " WHERE courses.teacher_id IN" +
            "(SELECT users.id FROM users" +
            " WHERE users.username = ?1)", nativeQuery = true)
    List<Course> findAllTeacherCourseByUsername(String username);
}
