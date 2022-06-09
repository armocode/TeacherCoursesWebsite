package com.demo.udema.service;

import com.demo.udema.entity.Lessons;

import java.util.List;

public interface LessonService {
    Lessons findByTitle(String title);
    Lessons findById(int id);
    Integer findLessonsSumByTitle(String title);
    Integer countLessonsByTitle(String title);
}
