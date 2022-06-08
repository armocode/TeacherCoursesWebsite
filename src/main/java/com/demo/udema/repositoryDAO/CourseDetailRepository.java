package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.CourseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDetailRepository extends JpaRepository<CourseDetails, Integer> {
}
