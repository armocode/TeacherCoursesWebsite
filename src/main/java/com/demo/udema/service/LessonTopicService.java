package com.demo.udema.service;

import com.demo.udema.entity.LessonTopics;

import java.util.List;

public interface LessonTopicService {
    List<LessonTopics> findAll();

    void save(LessonTopics lessonTopics);

    LessonTopics findById(int id);

    String findByListNumber(String listNumber);
    String findByTopicName(String name);
}
