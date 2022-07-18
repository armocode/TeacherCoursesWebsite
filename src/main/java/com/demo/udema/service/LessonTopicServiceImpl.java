package com.demo.udema.service;

import com.demo.udema.entity.Category;
import com.demo.udema.entity.LessonTopics;
import com.demo.udema.repositoryDAO.LessonTopicRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
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
    public String findByListNumber(int listNumber) {
        return lessonTopicRepository.findByListNumber(listNumber);
    }

    @Override
    public String findByTopicName(String name) {
        return lessonTopicRepository.findByTopicName(name);
    }

    @Override
    public List<LessonTopics> findAllTeacherLessonTopicByUsername(String username) {
        return lessonTopicRepository.findAllTeacherLessonTopicByUsername(username);
    }

    @Override
    public void deleteLessonTopicById(int id) {
        lessonTopicRepository.deleteById(id);
    }

    @Override
    public Integer findLessonTopicIdByLessonFkId(int id) {
        return lessonTopicRepository.findLessonTopicIdByLessonFkId(id);
    }

    @Override
    public List<LessonTopics> findAllLessonTopicByCourseTitle(String title) {
        return lessonTopicRepository.findAllLessonTopicByCourseTitle(title);
    }

    @Override
    public List<Integer> findLessonTopicIdByTeacherUsername(String username) {
        return lessonTopicRepository.findLessonTopicIdByTeacherUsername(username);
    }
}
