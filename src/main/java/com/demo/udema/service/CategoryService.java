package com.demo.udema.service;

import com.demo.udema.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    List<Category> getAll();
    Category findById(int id);

    void save(Category category);

    List<Category> findAllByOrderByTitleAsc();

    String findByTitle(String title);
    void deleteCategoryById(int id);
    List<Category> findAllTeacherCategoriesByUsername(String username);
    List<Category> findAllCategories();

}
