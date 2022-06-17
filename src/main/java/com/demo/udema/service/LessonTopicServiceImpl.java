package com.demo.udema.service;

import com.demo.udema.entity.Category;
import com.demo.udema.entity.LessonTopics;
import com.demo.udema.repositoryDAO.LessonTopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonTopicServiceImpl implements LessonTopicService{

    public LessonTopicRepository lessonTopicRepository;

    public LessonTopicServiceImpl(LessonTopicRepository lessonTopicRepository) {
        this.lessonTopicRepository = lessonTopicRepository;
    }


    @Override
    public List<LessonTopics> findAll() {
        return lessonTopicRepository.findAll();
    }

    @Override
    public void save(LessonTopics lessonTopics) {
        lessonTopicRepository.save(lessonTopics);
    }


    @Override
    public LessonTopics findById(int id) {
        Optional<LessonTopics> result = lessonTopicRepository.findById(id);
        LessonTopics lessonTop = null;
        if (result.isPresent()) {
            lessonTop = result.get();
        } else {
            throw new RuntimeException("Did not find category id: " + id);
        }
        return lessonTop;
    }

    @Override
    public String findByListNumber(String listNumber) {
        return lessonTopicRepository.findByListNumber(listNumber);
    }

}