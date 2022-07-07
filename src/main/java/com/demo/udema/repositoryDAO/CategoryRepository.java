package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT DISTINCT u FROM Category u JOIN u.course")
    List<Category> findAll();

    List<Category> findAllByOrderByTitleAsc();

    @Query(value = "SELECT title FROM categories " +
            "WHERE title LIKE ?1", nativeQuery = true)
    String findByTitle(String title);

    @Query(value = "SELECT * FROM categories" +
            " ORDER BY categories.id DESC", nativeQuery = true)
    List<Category> findAllCategories();
}
