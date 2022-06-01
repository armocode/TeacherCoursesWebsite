package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lessons, Integer> {

//    @Query(value = "SELECT * FROM lessons JOIN lesson_topics" +
//            " JOIN course_details JOIN courses" +
//            " WHERE courses.title=?1", nativeQuery = true)


//    @Query(value = "SELECT * FROM lessons", nativeQuery = true)
//    @Query("SELECT l FROM Lessons l")


//    @Query("SELECT l FROM Lessons l JOIN l.lessonTopics lt JOIN lt.courseDetails cd" +
//            " JOIN cd.course c WHERE c.title =?1")
            @Query(value = "SELECT lessons.name FROM lessons " +
                    "JOIN lesson_topics ON lessons.lesson_topic_id = lesson_topics.id" +
                    " JOIN course_details ON lesson_topics.course_details_id = course_details.id" +
                    " JOIN courses ON course_details.course_id = courses.id", nativeQuery = true)
    Lessons findByTitle(String title);


}






//@Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
//User findByEmailAddress(String emailAddress);