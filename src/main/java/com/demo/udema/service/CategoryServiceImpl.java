package com.demo.udema.service;

import com.demo.udema.entity.Category;
import com.demo.udema.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    public CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

        @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        // https://www.baeldung.com/java-optional
        Optional<Category> result = categoryRepository.findById(id);
        Category category = null;
        if (result.isPresent()) {
            category = result.get();
        } else {
            throw new RuntimeException("Did not find category id: " + id);
        }
        return category;
    }

}
