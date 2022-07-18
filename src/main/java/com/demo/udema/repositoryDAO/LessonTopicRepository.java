package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.LessonTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonTopicRepository extends JpaRepository<LessonTopics, Integer> {
    @Query(value = "SELECT * FROM lesson_topics",nativeQuery = true)
    List<LessonTopics> findAll();

    @Query(value = "SELECT list_number FROM lesson_topics" +
            " WHERE list_number = ?1", nativeQuery = true)
    String findByListNumber(int listNumber);

    @Query(value = "SELECT name FROM lesson_topics" +
            " WHERE name = ?1", nativeQuery = true)
    String findByTopicName(String name);

    @Query(value = "SELECT * FROM lesson_topics" +
            " WHERE lesson_topics.course_details_id IN" +
            "(SELECT course_details.id FROM course_details" +
            " WHERE course_details.course_id IN" +
            "(SELECT courses.id FROM courses" +
            " WHERE courses.teacher_id IN" +
            "(SELECT users.id FROM users" +
            " WHERE users.username = ?1)))", nativeQuery = true)
    List<LessonTopics> findAllTeacherLessonTopicByUsername(String username);

    @Query(value = "SELECT lesson_topics.id FROM lesson_topics" +
            " WHERE lesson_topics.id IN" +
            "(SELECT lessons.lesson_topic_id FROM lessons" +
            " WHERE lessons.lesson_topic_id = ?1)", nativeQuery = true)
    Integer findLessonTopicIdByLessonFkId(int id);

    @Query(value = "SELECT * FROM lesson_topics" +
            " WHERE lesson_topics.course_details_id IN" +
            "(SELECT course_details.id FROM course_details" +
            " WHERE course_details.course_id IN" +
            "(SELECT courses.id FROM courses"+
            " WHERE courses.title LIKE ?1))" +
            " ORDER BY lesson_topics.list_number", nativeQuery = true)
    List<LessonTopics> findAllLessonTopicByCourseTitle(String title);

    @Query(value = "SELECT id FROM lesson_topics" +
            " WHERE lesson_topics.course_details_id IN" +
            "(SELECT id FROM course_details" +
            " WHERE course_details.course_id IN" +
            " (SELECT id FROM courses" +
            "  WHERE courses.teacher_id IN" +
            "  (SELECT id FROM users" +
            "   WHERE users.username LIKE ?1)))", nativeQuery = true)
    List<Integer> findLessonTopicIdByTeacherUsername(String username);
}
