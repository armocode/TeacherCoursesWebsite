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
}
