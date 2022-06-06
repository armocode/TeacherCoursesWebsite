package com.demo.udema.controller;

import com.demo.udema.entity.Category;
import com.demo.udema.entity.Course;
import com.demo.udema.entity.CourseReviews;
import com.demo.udema.entity.User;
import com.demo.udema.service.CategoryService;
import com.demo.udema.service.CourseReviewService;
import com.demo.udema.service.CourseService;
import com.demo.udema.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
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
    public String index(@ModelAttribute("search") Course courseName, Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);

        if (courseName.getTitle() != "" && courseName.getTitle() != null) {
            List<Course> getByName = courseService.findAllByTitle(String.valueOf(courseName.getTitle()));
            if (getByName.size() == 0) {
                model.addAttribute("message", "No results found for '" + courseName.getTitle() + "' ");
            } else {
                model.addAttribute("message", "Displaying " + getByName.size() + " results for '" + courseName.getTitle() + "' ");
            }
            courseRatingAvg(getByName);
            model.addAttribute("courses", getByName);
        } else {
            List<Course> courseList = courseService.findAll();
            courseRatingAvg(courseList);
            model.addAttribute("courses", courseList);
        }
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

        courseReviewCountRatingByTitle(title, model);
        courseReviewRatingByTitle(title, model);
        lessonsSumByCourseTitle(title, model);
        lessonsCountByCourseTitle(title, model);

        return "course-detail";
    }

    @GetMapping("/reviews")
    public String adminPageReviews(Model model) {
//        return "admin-page/reviews";
        List<CourseReviews> sortByAnyTime = courseReviewService.findAllSortByAnyTime();
        model.addAttribute("reviewAnyTime", sortByAnyTime);
        return "reviews";
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

    @GetMapping("/404")
    public String accessDenied() {
        return "404";
    }


    /**
     * Course average rating
     *
     * @param courses
     */
    public void courseRatingAvg(List<Course> courses) {
        for (Course c : courses) {
            double sum = 0;
            double k = 0;
            double average = 0;
            if (c.getCourseDetails() != null) {
                for (CourseReviews cD : c.getCourseDetails().getCourseReviews()) {
                    sum += cD.getRating();
                    k++;
                }

                average = Math.round((sum / k) * 10) / 10d;
                c.setAvgRating(average);
            }
        }
    }

    /**
     * @param title SELECT AVG(rating) FROM reviews JOIN... WHERE c.title LIKE c.?
     * @param model If null, default rating is 0
     */
    public void courseReviewRatingByTitle(String title, Model model) {
        if (courseReviewService.findRatingByTitle(title) == null) {
            model.addAttribute("rating", 0);
        } else {
            model.addAttribute("rating", courseReviewService.findRatingByTitle(title));
        }
    }

    /**
     * @param title SELECT COUNT(rating) FROM reviews JOIN... WHERE c.title LIKE c.?
     */
    public void courseReviewCountRatingByTitle(String title, Model model) {
        if (courseReviewService.findRatingByTitle(title) == null) {
            model.addAttribute("countRating", 0);
        } else {
            model.addAttribute("countRating", courseReviewService.countRatingByTitle(title));
        }
    }

    /**
     * @param title SELECT SUM(length) FROM lessons JOIN... WHERE c.title LIKE c.?
     */
    public void lessonsSumByCourseTitle(String title, Model model) {
        model.addAttribute("sumLessons", lessonService.findLessonsSumByTitle(title));
    }

    /**
     * @param title SELECTS COUNT(id) FROM lessons JOIN... WHERE c.title LIKE c.?
     */
    public void lessonsCountByCourseTitle(String title, Model model) {
        model.addAttribute("countLessons", lessonService.countLessonsByTitle(title));
    }
}
