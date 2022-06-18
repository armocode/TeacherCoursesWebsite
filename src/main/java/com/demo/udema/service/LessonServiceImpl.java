package com.demo.udema.service;

import com.demo.udema.entity.Lessons;
import com.demo.udema.repositoryDAO.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService{

    public LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }


    @Override
    public Lessons findByTitle(String title) {
        return lessonRepository.findByTitle(title);
    }

    @Override
    public Lessons findById(int id) {
        Optional<Lessons> result = lessonRepository.findById(id);
        Lessons lessons = null;
        if(result.isPresent()) {
            lessons = result.get();
        } else {
            throw new RuntimeException("Did not found lesson id: "+id);
        }
        return lessons;
    }

    @Override
    public Integer findLessonsSumByTitle(String title) {
        return lessonRepository.findSumLessonByTitle(title);
    }

    @Override
    public Integer countLessonsByTitle(String title) {
        return lessonRepository.countLessonByTitle(title);
    }

    @Override
    public void save(Lessons lessons) {
        lessonRepository.save(lessons);
    }

    @Override
    public List<Lessons> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public String findByLessonName(String name) {
        return lessonRepository.findByLessonName(name);
    }

    @Override
    public List<Lessons> findAllTeacherLessonsByUsername(String username) {
        return lessonRepository.findAllTeacherLessonsByUsername(username);
    }

}
