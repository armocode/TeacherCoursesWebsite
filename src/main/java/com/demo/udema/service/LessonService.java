package com.demo.udema.service;

import com.demo.udema.entity.Lessons;

import java.util.List;

public interface LessonService {
//    List<Lessons> findAll();

    List<Lessons> findAllByTitle(String title);

    Lessons findById(int id);
}
