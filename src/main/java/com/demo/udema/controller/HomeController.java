package com.demo.udema.controller;

import com.demo.udema.entity.*;
import com.demo.udema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.demo.udema.service.CategoryService;
import com.demo.udema.service.CourseReviewService;
import com.demo.udema.service.CourseService;
import com.demo.udema.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    private CategoryService categoryService;
    private CourseService courseService;
    private CourseReviewService courseReviewService;
    private LessonService lessonService;
    private CourseDetailService courseDetailService;

    @Autowired
    public HomeController(CategoryService categoryService, CourseService courseService, CourseReviewService courseReviewService, LessonService lessonService, CourseDetailService courseDetailService) {
        this.categoryService = categoryService;
        this.courseService = courseService;
        this.courseReviewService = courseReviewService;
        this.lessonService = lessonService;
        this.courseDetailService = courseDetailService;
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

    @GetMapping("/reviews")
    public String adminPageReviews(@ModelAttribute("orderReviews") String arrangement, Model model) {
        List<CourseReviews> oldestReview = courseReviewService.findAllSortByAnyTime();
        List<CourseReviews> latestReview = courseReviewService.findAllSortByLatest();

        if (arrangement == null) {
            model.addAttribute("review", oldestReview);
            return "admin-page/reviews";
        }
        if (arrangement.equals("latest")) {
            model.addAttribute("review", latestReview);
            return "admin-page/reviews";
        } else if (arrangement.equals("oldest")) {
            model.addAttribute("review", oldestReview);
            return "admin-page/reviews";
        }
        model.addAttribute("review", oldestReview);
        return "admin-page/reviews";
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

    @GetMapping("/addListing")
    public String addListing(@AuthenticationPrincipal UserDetails loggerUser, Model model) {
        String username = loggerUser.getUsername();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);

        List<Category> categoriesList = categoryService.getAll();
        model.addAttribute("categoriesList", categoriesList);

        model.addAttribute("details", new CourseDetails());

        model.addAttribute("course", new Course());
        return "admin-page/add-listing";
    }

    @PostMapping("/addListing")
    public String addListing(@ModelAttribute("course") Course course,
                             @ModelAttribute("user") User user,
                             @RequestParam HashMap<String, String> categoriesList,
                             @ModelAttribute("details") CourseDetails courseDetails) {
        // Pasiemu Vartotjo ID ir setinu i course
        User searchUser = userService.findByUsername(user.getUsername());
        course.setUsers(searchUser);
        // Setina kategorija TODO sutvarkyti su null (VALIDACIJOJ)
        Category searchCategory = categoryService.findById(Integer.parseInt(categoriesList.get("catId")));
        course.setCategory(searchCategory);
        // Issaugom kursa
        courseService.save(course);
        // Pasiem issaugoto kurso title
        Course newCourseTitle = courseService.findByTitle(course.getTitle());
        // setinam id
        courseDetails.setCourse(newCourseTitle);
        // Issaugom id i details
        courseDetailService.save(courseDetails);
        return "redirect:/addListing";
    }


    @GetMapping("/addCategory")
    public String addCategory(Model model, Category category) {
//        List<Category> categoryList = categoryService.findAll();
//        model.addAttribute("categoryList", categoryList);

        model.addAttribute("newCategory", new Category());
        System.out.println("\n 1");
        return "admin-page/add-category";

    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("category") Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        RedirectAttributes redirectAttributes
        if(bindingResult.hasFieldErrors("title")) {
            System.out.println("2");
            return "redirect:/admin-page/add-category";
        }
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "test 3");
        System.out.println(" 3 ");
        return "redirect:/admin-page/add-category";
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
     * @param title SELECT COUNT(id) FROM lessons JOIN... WHERE c.title LIKE c.?
     */
    public void lessonsCountByCourseTitle(String title, Model model) {
        model.addAttribute("countLessons", lessonService.countLessonsByTitle(title));
    }
}
