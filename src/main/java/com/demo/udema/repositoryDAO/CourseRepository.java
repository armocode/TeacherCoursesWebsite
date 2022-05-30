package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByCategoryId(int id);

    List<Course> findAll();
    void deleteByTitle(String title);

    Course findByTitle(String title);


//    @Query("SELECT a FROM Course a JOIN a.courseDetails WHERE a.title LIKE '"+title+"'")
    List<Course> findAllByTitle(String title);

}
