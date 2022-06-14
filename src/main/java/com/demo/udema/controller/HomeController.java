package com.demo.udema.controller;

import com.demo.udema.entity.*;
import com.demo.udema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.List;

@Controller
public class HomeController implements ErrorController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseValidator courseValidator;
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
            getCourseRatingAvgAndLenghtSum(getByName);
            model.addAttribute("courses", getByName);
        } else {
            List<Course> courseList = courseService.findAll();
            getCourseRatingAvgAndLenghtSum(courseList);
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
        getCourseRatingAvgAndLenghtSum(course);
        model.addAttribute("courses", course);
        // Categories list
        List<Category> categoriesList = categoryService.findAll();
        model.addAttribute("categoriesList", categoriesList);
        return "courses-list";
    }

    @GetMapping("/coursesDetails")
    public String course(@RequestParam("courseTitle") String title, Model model) {

        model.addAttribute("userBoughtCourse", usersBoughtCourse(title));

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

    @GetMapping("/addCourse")
    public String addCourse(@AuthenticationPrincipal UserDetails loggerUser, Model model) {
        String username = loggerUser.getUsername();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);

        List<Category> categoriesList = categoryService.getAll();
        model.addAttribute("categoriesList", categoriesList);

        model.addAttribute("details", new CourseDetails());

        model.addAttribute("course", new Course());
        return "admin-page/add-course";
    }

    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute("course") Course course,
                            BindingResult resultCourse,
                            @ModelAttribute("details") CourseDetails courseDetails,
                            BindingResult resultDetail,
                            @ModelAttribute("user") User user,
                            @RequestParam HashMap<String, String> categoriesList,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        courseValidator.validate(course, resultCourse);
        courseValidator.validateCourseDes(courseDetails, resultDetail);

        if (resultCourse.hasErrors() || resultDetail.hasErrors()) {
            List<Category> categoriesList2 = categoryService.getAll();              // Is naujo uzkrauname kategoriju sarasa, po valid lieka tuscias
            model.addAttribute("categoriesList", categoriesList2);
            model.addAttribute("errormessage", "Failed to create course");
            return "admin-page/add-course";
        }
        if (categoriesList.get("catId") == null || categoriesList.get("catId") == ""){
            model.addAttribute("error", "Please select an option from the list");
            List<Category> categoriesList2 = categoryService.getAll();
            model.addAttribute("categoriesList", categoriesList2);
            model.addAttribute("errormessage", "Failed to create course");
            return "admin-page/add-course";
        }
        User searchUser = userService.findByUsername(user.getUsername());           // Pasiemu Vartotjo ID ir setinu i course
        course.setUsers(searchUser);
        Category searchCategory = categoryService.findById(Integer.parseInt(categoriesList.get("catId")));
        course.setCategory(searchCategory);
        courseService.save(course);
        Course newCourseTitle = courseService.findByTitle(course.getTitle());       // Pasiem issaugoto kurso title
        courseDetails.setCourse(newCourseTitle);                                    // setinam id
        courseDetailService.save(courseDetails);                                    // Issaugom id i details
        redirectAttributes.addFlashAttribute("message", "Course saved successfully");
        return "redirect:/addCourse";
    }

    @GetMapping("/addLesson")
    public String addLesson(Model model) {
//        return "admin-page/add-lesson";
        return "add-lesson";
    }

    @PostMapping("/addLesson")
    public String addLesson(@ModelAttribute("newLesson") Lessons lessons, Model model, RedirectAttributes redirectAtt) {


        return "add-lesson";
    }

    @GetMapping("/addCategory")
    public String addCategory(Model model) {
        model.addAttribute("newCategory", new Category());
        return "admin-page/add-category";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("newCategory") Category category, BindingResult resultCat, Model model, RedirectAttributes redirectAttributes) {
        courseValidator.validateCategory(category, resultCat);
        if(resultCat.hasErrors()){
            model.addAttribute("errormessage", "Failed to create category");
            return "admin-page/add-category";
        }
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Category saved successfully");
        return "redirect:/addCategory";
    }

    @GetMapping("/coursesListAll")
    public String coursesListAll(Model model) {
        List<Course> course = courseService.findAll();
        getCourseRatingAvgAndLenghtSum(course);
        model.addAttribute("courses", course);
        // Categories list
        List<Category> categoriesList = categoryService.findAll();
        model.addAttribute("categoriesList", categoriesList);
        return "courses-list";
    }

    @GetMapping("/coursesGridAll")
    public String coursesGridAll(Model model) {
        List<Course> course = courseService.findAll();
        getCourseRatingAvgAndLenghtSum(course);
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
        getCourseRatingAvgAndLenghtSum(course);
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

    @RequestMapping("/error")
    public ModelAndView handleError() {
        // https://www.techiedelight.com/display-custom-error-pages-in-spring-boot/
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404");
        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return "error";
    }


    /**
     * Course average rating
     * @param courses
     */
    public void getCourseRatingAvgAndLenghtSum(List<Course> courses) {
        for (Course c : courses) {
            int sumL = 0;
            double sum = 0;
            double k = 0;
            double average = 0;
            if (c.getCourseDetails() != null) {
                for (CourseReviews cD : c.getCourseDetails().getCourseReviews()) {
                    sum += cD.getRating();
                    k++;
                }
                for (LessonTopics lessonTopics : c.getCourseDetails().getLessonTopics()) {
                    for(Lessons lessons : lessonTopics.getLessonsList()){
                        sumL += lessons.getLength();
                    }
                }
                average = Math.round((sum / k) * 10) / 10d;
                c.setAvgRating(average);
                c.setSumLessonsLenght(sumL);
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


    /**
     * Check if user logged in or anonymous
     * @return logged username or anonymous
     */
    public String currentLoggedInUsername() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            System.out.println(username + "  - Logged");
        } else {
            username = principal.toString();
            System.out.println(username);
        }
        return username;
    }

    /**
     * @param courseTitle, Check logged user who bought course by c.title
     * @return true if bought course, else false
     */
    public Boolean usersBoughtCourse(String courseTitle) {

        List<String> us = userService.findUsersWhoBoughtCourseByCourseTitle(courseTitle);
        if (us.contains(currentLoggedInUsername())) {
            System.out.println(currentLoggedInUsername() + " <-- Logged user - equals user -->" + us.contains(currentLoggedInUsername()));
            return true;
        }
        return false;
    }
}