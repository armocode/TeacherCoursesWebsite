package com.demo.udema.service;

import com.demo.udema.entity.Category;
import com.demo.udema.entity.CourseDetails;
import com.demo.udema.repositoryDAO.CourseDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public CourseDetails findById(int id) {
        Optional<CourseDetails> result = courseDetailRepository.findById(id);
        CourseDetails csdet = null;
        if (result.isPresent()) {
            csdet = result.get();
        } else {
            throw new RuntimeException("Did not find category id: " + id);
        }
        return csdet;
    }
}
