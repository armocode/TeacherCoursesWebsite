package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lessons, Integer> {
    @Query(value = "SELECT lessons.name FROM lessons " +
            "JOIN lesson_topics ON lessons.lesson_topic_id = lesson_topics.id" +
            " JOIN course_details ON lesson_topics.course_details_id = course_details.id" +
            " JOIN courses ON course_details.course_id = courses.id", nativeQuery = true)
    Lessons findByTitle(String title);

    @Query(value = "SELECT SUM(lessons.length) FROM lessons" +
            " JOIN lesson_topics ON lesson_topics.id = lessons.lesson_topic_id" +
            " JOIN course_details ON lesson_topics.course_details_id = course_details.id" +
            " WHERE course_details.course_id IN" +
            "(SELECT courses.id FROM courses" +
            " WHERE courses.title LIKE ?1)", nativeQuery = true)
    Integer findSumLessonByTitle(String title);

    @Query(value = "SELECT COUNT(lessons.id) FROM lessons" +
            " JOIN lesson_topics ON lesson_topics.id = lessons.lesson_topic_id" +
            " JOIN course_details ON lesson_topics.course_details_id = course_details.id" +
            " WHERE course_details.course_id IN" +
            "(SELECT courses.id FROM courses" +
            " WHERE courses.title LIKE ?1)", nativeQuery = true)
    Integer countLessonByTitle(String title);


}


