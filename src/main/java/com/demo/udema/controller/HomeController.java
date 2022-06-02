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


    //DELETE THIS COMMENT
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
        return "index";
    }


    @GetMapping("/coursesList")
    public String coursesList(@RequestParam("categoryId") int id, Model model) {
        model.addAttribute("catId", id);
        Category category = categoryService.findById(id);
        model.addAttribute("categories", category);
        List<Course> course = courseService.findAllByCategoryId(id);
        courseRatingAvg(course);
        model.addAttribute("courses", course);
        // Categories list
        List<Category> categoriesList = categoryService.findAll();
        model.addAttribute("categoriesList", categoriesList);
        return "courses-list";
    }

    @GetMapping("/coursesDetails")
    public String course(@RequestParam("courseTitle") String title, Model model) {
        Course course = courseService.findByTitle(title);
        model.addAttribute("coursesTit", course);

        List<CourseReviews> courseReviewsList = courseReviewService.findAllByTitle(title);
        model.addAttribute("reviewList", courseReviewsList);

        double courseRev = courseReviewService.findRatingByTitle(title);
        model.addAttribute("rating", courseRev);

        return "course-detail";
    }
    @GetMapping("/addListing")
    public String addListing(@ModelAttribute("course") Course course, BindingResult bindingResult) {
        courseService.save(course);
        return "admin-page/add-listing";
    }
    @PostMapping("/addListing/update")
    public String addListing(@ModelAttribute("courseUpd") Course course) {
        courseService.save(course);
        return "redirect:/admin-page/add-listing";
    }

    @GetMapping("/coursesListAll")
    public String coursesListAll(Model model) {
        List<Course> course = courseService.findAll();
        courseRatingAvg(course);
        model.addAttribute("courses", course);
        // Categories list
        List<Category> categoriesList = categoryService.findAll();
        model.addAttribute("categoriesList", categoriesList);
        return "courses-list";
    }

    @GetMapping("/coursesGridAll")
    public String coursesGridAll(Model model) {
        List<Course> course = courseService.findAll();
        courseRatingAvg(course);
        model.addAttribute("courses", course);
        // Categories list
        List<Category> categoriesList = categoryService.findAll();
        model.addAttribute("categoriesList", categoriesList);
        return "courses-grid";
    }

    @GetMapping("/coursesGrid")
    public String coursesGrid(@RequestParam("categoryId") int id, Model model) {
        model.addAttribute("catId", id);
        Category category = categoryService.findById(id);
        model.addAttribute("categories", category);
        List<Course> course = courseService.findAllByCategoryId(id);
        courseRatingAvg(course);
        model.addAttribute("courses", course);
        // Categories list
        List<Category> categoriesList = categoryService.findAll();
        model.addAttribute("categoriesList", categoriesList);
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


    /**
     * Course average rating
     * @param courses
     */
    public void courseRatingAvg(List<Course> courses) {
        for (Course c : courses) {
            double sum = 0;
            int k = 0;
            double average = 0;
            if (c.getCourseDetails() != null) {
                for (CourseReviews cD : c.getCourseDetails().getCourseReviews()) {
                    sum += cD.getRating();
                    k++;
                }
                average = sum / k;
                c.setAvgRating(average);
            }
        }
    }
}
