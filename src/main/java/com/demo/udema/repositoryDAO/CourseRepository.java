package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.Course;
import com.demo.udema.entity.CourseReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByCategoryId(int id);

    List<Course> findAll();
    void deleteByTitle(String title);

    Course findByTitle(String title);

    @Query(value = "SELECT * FROM courses" +
            " WHERE courses.title LIKE %?1%" +
            " GROUP BY courses.title", nativeQuery = true)
    List<Course> findAllByTitle(String title);


}
