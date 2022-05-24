package com.demo.udema.repository;

import com.demo.udema.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
//    @Query(value = "SELECT title FROM Category title")
    List<Category> findAll();
}
