package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lessons, Integer> {


//    @Query(value = "SELECT DISTINCT c FROM Lessons l " +
//            "JOIN l.lessonTopics lt" +
//            " JOIN lt.courseDetails cd" +
//            " JOIN cd.course c")


//    @Query(value = "SELECT * FROM lessons JOIN lesson_topics" +
//            " JOIN course_details JOIN courses" +
//            " WHERE courses.title=?1", nativeQuery = true)


//    @Query(value = "SELECT * FROM lessons", nativeQuery = true)
    @Query("SELECT l FROM Lessons l")
//    @Query("SELECT l FROM Lessons l JOIN l.lessonTopics lt JOIN CourseDetails cd" +
//            " JOIN cd.courseReviews cr")
    List<Lessons> findAllByTitle(String title);


}






//@Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
//User findByEmailAddress(String emailAddress);