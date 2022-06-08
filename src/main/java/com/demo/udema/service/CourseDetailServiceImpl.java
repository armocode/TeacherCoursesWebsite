package com.demo.udema.service;

import com.demo.udema.entity.CourseDetails;
import com.demo.udema.repositoryDAO.CourseDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseDetailServiceImpl implements CourseDetailService {
    public CourseDetailRepository courseDetailRepository;

    public CourseDetailServiceImpl(CourseDetailRepository courseDetailRepository) {
        this.courseDetailRepository = courseDetailRepository;
    }

    @Override
    public List<CourseDetails> findAll() {
        return courseDetailRepository.findAll();
    }

    @Override
    public void save(CourseDetails details) {
        courseDetailRepository.save(details);
    }
}
