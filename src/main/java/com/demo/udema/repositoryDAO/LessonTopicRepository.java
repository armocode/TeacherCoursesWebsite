package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.LessonTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonTopicRepository extends JpaRepository<LessonTopics, Integer> {
    @Query(value = "SELECT * FROM lesson_topics",nativeQuery = true)
    List<LessonTopics> findAll();


}
