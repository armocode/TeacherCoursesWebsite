package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT DISTINCT u FROM Category u JOIN u.course")
    List<Category> findAll();

    List<Category> findAllByOrderByTitleAsc();


}
