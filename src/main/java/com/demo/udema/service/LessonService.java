package com.demo.udema.service;

import com.demo.udema.entity.LessonTopics;
import com.demo.udema.entity.Lessons;

import java.util.List;

public interface LessonService {
    Lessons findByTitle(String title);
    Lessons findById(int id);
    Integer findLessonsSumByTitle(String title);
    Integer countLessonsByTitle(String title);
    void save(Lessons lessons);
    List<Lessons> findAll();
    String findByLessonName(String name);
    List<Lessons> findAllTeacherLessonsByUsername(String username);
    void deleteLessonById(int id);
    String findByListNumber(String listNumber);
    List<Integer> findLessonsIdByTeacherUsername(String username);

}
