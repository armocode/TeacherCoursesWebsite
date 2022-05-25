package com.demo.udema.controller;

import com.demo.udema.entity.Category;
import com.demo.udema.entity.Course;
import com.demo.udema.entity.CourseReviews;
import com.demo.udema.service.CategoryService;
import com.demo.udema.service.CourseReviewService;
import com.demo.udema.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private CategoryService categoryService;
    private CourseService courseService;
    private CourseReviewService courseReviewService;

    @Autowired
    public HomeController(CategoryService categoryService, CourseService courseService) {
        this.categoryService = categoryService;
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String index(Model model){
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);
        List<Course> courseList = courseService.findAll();
        model.addAttribute("courses", courseList);
//        List<CourseReviews> courseReviews = courseReviewService.findAll();
//        model.addAttribute("coursesReview", courseReviews);
        return "index";
    }

    @GetMapping("/coursesList")
    public String coursesList(@RequestParam("categoryId") int id, Model model){
        Category category = categoryService.findById(id);
        model.addAttribute("categories", category);
        List<Course> course = courseService.findAllByCategoryId(id);
        model.addAttribute("courses", course);
        return "courses-list";
    }

    @GetMapping("/coursesGrid")
    public String coursesGrid() {
        return "courses-grid";
    }

    @GetMapping("/courseDetail")
    public String courseDetail() {
        return "course-detail";
    }

    @GetMapping("/coursesGridSidebar")
    public String coursesGridSidebar() {
        return "courses-grid-sidebar";
    }
}
