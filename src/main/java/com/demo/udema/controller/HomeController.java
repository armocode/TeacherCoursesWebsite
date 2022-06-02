package com.demo.udema.controller;

import com.demo.udema.entity.Category;
import com.demo.udema.entity.Course;
import com.demo.udema.entity.CourseReviews;
import com.demo.udema.service.CategoryService;
import com.demo.udema.service.CourseReviewService;
import com.demo.udema.service.CourseService;
import com.demo.udema.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {
    private CategoryService categoryService;
    private CourseService courseService;
    private CourseReviewService courseReviewService;
    private LessonService lessonService;

    @Autowired
    public HomeController(CategoryService categoryService, CourseService courseService, CourseReviewService courseReviewService, LessonService lessonService) {
        this.categoryService = categoryService;
        this.courseService = courseService;
        this.courseReviewService = courseReviewService;
        this.lessonService = lessonService;
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

    @GetMapping("/coursesDetails")
    public String course(@RequestParam("courseTitle") String title, Model model) {

        Course course = courseService.findByTitle(title);
        model.addAttribute("coursesTit", course);

        CourseReviews courseReviews = courseReviewService.findByTitle(title);
        model.addAttribute("review", courseReviews);

        double courseRev = courseReviewService.findRatingByTitle(title);
        model.addAttribute("rating", courseRev);

        return "course-detail";


//        List<CourseReviews> courseReviewsList = courseReviewService.findAllByTitle(title);
//        model.addAttribute("ivertinimuList", courseReviewsList);

//        Lessons lesson = lessonService.findByTitle(title);
//        model.addAttribute("lessonTit", lesson);

    }
    @GetMapping("/addListing")
    public String addListing(@ModelAttribute("course") Course course, BindingResult bindingResult) {
        courseService.save(course);
        return "admin-page/add-listing";
    }
    @PostMapping("/addListing/update")
    public String addListing(@ModelAttribute("courseUpd") Course course) {
        courseService.save(course);
//        Course course = courseService.findByTitle(title);
//        model.addAttribute("coursesTit", course);
//        List<Course> courseListTit = courseService.findAllByTitle(title);
//        model.addAttribute("coursesListTit", courseListTit);
//        List<Category> categoryList = categoryService.findAll();
//        model.addAttribute("categoriesList", categoryList);
//        List<Course> courseList = courseService.findAll();
//        model.addAttribute("coursesList", courseList);
        return "redirect:/admin-page/add-listing";
    }

    @GetMapping("/coursesGrid")
    public String coursesGrid( Model model) {
        List<Course> courseList = courseService.findAll();
        model.addAttribute("courses", courseList);
        return "courses-grid";
    }


    @GetMapping("/coursesGridSidebar")
    public String coursesGridSidebar() {
        return "courses-grid-sidebar";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contacts")
    public String contact() {
        return "contacts";
    }
}
