package com.demo.udema.service;


import com.demo.udema.entity.CourseDetails;

import java.util.List;

public interface CourseDetailService {
    List<CourseDetails> findAll();

    void save(CourseDetails details);


}
