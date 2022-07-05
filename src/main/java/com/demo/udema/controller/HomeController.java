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

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Controller
public class HomeController implements ErrorController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseValidator courseValidator;
    private OrderService orderService;
    private CategoryService categoryService;
    private CourseService courseService;
    private CourseReviewService courseReviewService;
    private LessonService lessonService;
    private CourseDetailService courseDetailService;
    private LessonTopicService lessonTopicService;

    @Autowired
    public HomeController(UserService userService, CourseValidator courseValidator, OrderService orderService, CategoryService categoryService, CourseService courseService, CourseReviewService courseReviewService, LessonService lessonService, CourseDetailService courseDetailService, LessonTopicService lessonTopicService) {
        this.userService = userService;
        this.courseValidator = courseValidator;
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.courseService = courseService;
        this.courseReviewService = courseReviewService;
        this.lessonService = lessonService;
        this.courseDetailService = courseDetailService;
        this.lessonTopicService = lessonTopicService;
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

    @GetMapping("/addCourse")
    public String addCourse(@AuthenticationPrincipal UserDetails loggerUser, Model model) {
        String username = loggerUser.getUsername();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);

        List<Category> categoriesList = categoryService.getAll();
        model.addAttribute("categoriesList", categoriesList);

        model.addAttribute("cDetails", new CourseDetails());

        model.addAttribute("course", new Course());

        model.addAttribute("coursesList", courseService.findAllTeacherCourseByUsername(username));

        return "admin-page/add-course";
    }

    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute("course") Course course,
                            BindingResult resultCourse,
                            @ModelAttribute("cDetails") CourseDetails courseDetails,
                            BindingResult resultDetail,
                            @ModelAttribute("user") User user,
                            @RequestParam HashMap<String, String> categoriesList,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        int tempId = course.getId();                                                // For redirect (/editCourse)
        courseValidator.validate(course, resultCourse);
        courseValidator.validateCourseDes(courseDetails, resultDetail);
        if (resultCourse.hasErrors() || resultDetail.hasErrors()) {
            List<Category> categoriesList2 = categoryService.getAll();               // Is naujo uzkrauname kategoriju sarasa, po valid lieka tuscias
            model.addAttribute("categoriesList", categoriesList2);
            model.addAttribute("errormessage", "Failed to create course");
            return "admin-page/add-course";
        }
        if (categoriesList.get("catId") == null || categoriesList.get("catId") == "") {
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
        Course newCourseTitle = courseService.findByTitle(course.getTitle());       // Pasiemame ka tik issaugoto kurso title (id)
        courseDetails.setCourse(newCourseTitle);                                    // Setinam id i details ->course_id
        courseDetailService.save(courseDetails);                                    // issaugom
        if (tempId != 0) {                                                              // Redirect (/editCourse)
            redirectAttributes.addFlashAttribute("message", "Course update successfully");
            return "redirect:/editCourse";
        }
        redirectAttributes.addFlashAttribute("message", "Course saved successfully");
        return "redirect:/addCourse";
    }

    @GetMapping("/deleteCourse")
    public String deleteCourse(@ModelAttribute("courseId") int courseId,
                               @AuthenticationPrincipal UserDetails loggerUser,
                               RedirectAttributes redirectAtt) {
        Course getCourseById =  courseService.findById(courseId);
        List<Orders> getOrdersListByCourseId = orderService.findAllByCourseId(courseId);
        // Jei nepriklauso teacher kursas, neleidziame jo trinti
        List<Course> courseByUsername = courseService.findAllTeacherCourseByUsername(loggerUser.getUsername());
        for (Course c : courseByUsername){
            if (c.getId() == courseId){
                if(getCourseById.getCourseDetails().getLessonTopics().size() == 0){
                    // Triname orderius
                    if(getOrdersListByCourseId != null){
                        int tempId = 0;
                        for (Orders o : orderService.findAllByCourseId(courseId)){
                            tempId = o.getCourseId();
                        }
                        orderService.deleteByCourseId(tempId);
                    }
                    // Triname Course details
                    courseDetailService.deleteById(getCourseById.getCourseDetails().getIdDet());
                    // Triname Course
                    courseService.deleteById(getCourseById.getId());
                    redirectAtt.addFlashAttribute("message", "Course deleted successfully");
                    return "redirect:/addCourse";
                } else {
                    redirectAtt.addFlashAttribute("errormessage", "If you want to delete course at first you must delete lesson topic");
                    return "redirect:/addCourse";
                }
            }
        }
        return "redirect:/error";
    }

    @GetMapping("/editCourse")
    public String editCourse(@AuthenticationPrincipal UserDetails loggerUser, Model model) {
        String username = loggerUser.getUsername();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        if (user.getRole().equals("ROLE_ADMIN")) {
            List<Course> getAllCourses = courseService.findAll();
            model.addAttribute("courses", getAllCourses);
            return "admin-page/edit-course";
        } else {
            List<Course> getAllCoursesByTeacherUsername = courseService.findAllTeacherCourseByUsername(loggerUser.getUsername());
            model.addAttribute("courses", getAllCoursesByTeacherUsername);
            return "admin-page/edit-course";
        }
    }

    @GetMapping(value = "/showEditCourse")
    public String showEditCourse(@ModelAttribute("courseId") int courseId,
                                 @AuthenticationPrincipal UserDetails loggerUser, Model model) {
        String username = loggerUser.getUsername();
        Course course = courseService.findById(courseId);
        List<Category> categoriesList = categoryService.getAll();
        List<Course> courseByUsername = courseService.findAllTeacherCourseByUsername(username);
        for (Course c : courseByUsername) {
            if (c.getId() == courseId) {
                model.addAttribute("user", userService.findByUsername(username));
                model.addAttribute("course", course);
                model.addAttribute("cDetails", course.getCourseDetails());
                model.addAttribute("categoriesList", categoriesList);
                return "admin-page/add-course";
            }
        }
        // Jei useriui nepriklauso kursas arba ivede nesamone, tai useri nusiuncia į /error pspl
        return "redirect:/error";
    }

    @GetMapping(value = "/showEditLessonTopic")
    public String showEditLessonTopic(@ModelAttribute("lesTopId") int lessonTopicsId,
                                      @AuthenticationPrincipal UserDetails loggerUser, Model model) {
        String username = loggerUser.getUsername();
        LessonTopics lessonTopicById = lessonTopicService.findById(lessonTopicsId);
        List<LessonTopics> lessonsTopicsByUsername = lessonTopicService.findAllTeacherLessonTopicByUsername(loggerUser.getUsername());
        for (LessonTopics l : lessonsTopicsByUsername) {
            if (l.getId() == lessonTopicsId) {
                model.addAttribute("courseDet", lessonTopicById.getCourseDetails());
                model.addAttribute("lesTop", lessonTopicById);
                model.addAttribute("lesson_top", lessonTopicService.findAllTeacherLessonTopicByUsername(username));
                return "admin-page/add-lesson-topic";
            }
        }
        return "redirect:/error";
    }

    @GetMapping("/addLessonTopic")
    public String addLessonTopic(Model model) {
        List<LessonTopics> teacherLessonTopicList = lessonTopicService.findAllTeacherLessonTopicByUsername(currentLoggedInUsername());
        model.addAttribute("lesson_top", teacherLessonTopicList);
        List<CourseDetails> teacherCourseDetList = courseDetailService.findAllTeacherCourseDetailsByUsername(currentLoggedInUsername());
        model.addAttribute("courseDet", teacherCourseDetList);

        model.addAttribute("lesTop", new LessonTopics());
        return "admin-page/add-lesson-topic";
    }

    @PostMapping("/addLessonTopic")
    public String addLessonTopic(@ModelAttribute("lesTop") LessonTopics lessonTopics,
                                 BindingResult lessonTopicResult,
                                 @ModelAttribute("courseDet") CourseDetails courseDetails,
                                 Model model, RedirectAttributes redirectAtt,
                                 @RequestParam HashMap<String, String> mapList) {
        int tempId = lessonTopics.getId();
        courseValidator.validateLessonTopic(lessonTopics, lessonTopicResult);
        List<LessonTopics> teacherLessonTopicList = lessonTopicService.findAllTeacherLessonTopicByUsername(currentLoggedInUsername());

        if (lessonTopicResult.hasErrors()) {
            model.addAttribute("lesson_top", teacherLessonTopicList);

            if (lessonTopics.getId() == 0) {
                List<CourseDetails> teacherCourseDetList = courseDetailService.findAllTeacherCourseDetailsByUsername(currentLoggedInUsername());
                model.addAttribute("courseDet", teacherCourseDetList);
            } else {
                LessonTopics lessonTopicById = lessonTopicService.findById(lessonTopics.getId());
                model.addAttribute("courseDet", lessonTopicById.getCourseDetails());
            }

            model.addAttribute("error", "Failed to create lesson topic");
            return "admin-page/add-lesson-topic";
        }

        if (mapList.get("csDetId").equals("csDetNotSelected")) {
            model.addAttribute("lesson_top", teacherLessonTopicList);

            if (lessonTopics.getId() == 0) {
                List<CourseDetails> teacherCourseDetList = courseDetailService.findAllTeacherCourseDetailsByUsername(currentLoggedInUsername());
                model.addAttribute("courseDet", teacherCourseDetList);
            } else {
                LessonTopics lessonTopicById = lessonTopicService.findById(lessonTopics.getId());
                model.addAttribute("courseDet", lessonTopicById.getCourseDetails());
            }
            model.addAttribute("error", "Please select an option from course details list");
            return "admin-page/add-lesson-topic";
        }
        CourseDetails csDet = courseDetailService.findById(Integer.parseInt(mapList.get("csDetId")));
        lessonTopics.setCourseDetails(csDet);
        lessonTopicService.save(lessonTopics);
        redirectAtt.addFlashAttribute("message", "Lesson topic saved successfully");
        if (tempId != 0) {
            redirectAtt.addFlashAttribute("message", "Lesson topic update successfully");
            return "redirect:/editCourse";
        }
        return "redirect:/addLessonTopic";
    }

    @GetMapping("deleteLessonTopic/{id}")
    public String deleteLessonTopic(@PathVariable(value = "id") int id, RedirectAttributes redirectAtt) {
        if (checkTeacherLessonTopicsIdByTeacherUsername(id)) {
            if (lessonTopicService.findLessonTopicIdByLessonFkId(id) == null) {
                redirectAtt.addFlashAttribute("message", "Lesson topic deleted successfully");
                this.lessonTopicService.deleteLessonTopicById(id);
                return "redirect:/addLessonTopic";
            } else if (lessonTopicService.findLessonTopicIdByLessonFkId(id) != null) {
                redirectAtt.addFlashAttribute("error", "If you want to delete lesson topic at first you must delete lesson");
                return "redirect:/addLessonTopic";
            }
        }
        return "redirect:/error";
    }

    @GetMapping(value = "/showEditLesson")
    public String showEditLesson(@ModelAttribute("lesId") int lesId,
                                 @AuthenticationPrincipal UserDetails loggerUser, Model model) {
        List<Lessons> lessonsByUsername = lessonService.findAllTeacherLessonsByUsername(loggerUser.getUsername());
        Lessons lessonById = lessonService.findById(lesId);
        for (Lessons l : lessonsByUsername) {
            if (l.getId() == lesId) {
                model.addAttribute("lesson_top", lessonById.getLessonTopics());
                model.addAttribute("lessons", lessonsByUsername);
                model.addAttribute("lesson", lessonById);
                return "admin-page/add-lesson";
            }
        }
        return "redirect:/error";
    }

    @GetMapping("/addLesson")
    public String addLesson(Model model) {
        List<LessonTopics> teacherLessonTopicList = lessonTopicService.findAllTeacherLessonTopicByUsername(currentLoggedInUsername());
        model.addAttribute("lesson_top", teacherLessonTopicList);

        List<Lessons> teacherLessonList = lessonService.findAllTeacherLessonsByUsername(currentLoggedInUsername());
        model.addAttribute("lessons", teacherLessonList);
        model.addAttribute("lesson", new Lessons());
        return "admin-page/add-lesson";
    }

    @PostMapping("/addLesson")
    public String addLesson(@ModelAttribute("lesTop") LessonTopics lessonTopics,
                            @ModelAttribute("lesson") Lessons lessons,
                            BindingResult lessonResult,
                            Model model, RedirectAttributes redirectAtt,
                            @RequestParam HashMap<String, String> mapList) {
        int tempId = lessons.getId();
        courseValidator.validateLesson(lessons, lessonResult);
        List<Lessons> teacherLessonList = lessonService.findAllTeacherLessonsByUsername(currentLoggedInUsername());

        if (lessonResult.hasErrors()) {
            model.addAttribute("lessons", teacherLessonList);

            if (tempId == 0) {
                List<LessonTopics> teacherLessonTopicList = lessonTopicService.findAllTeacherLessonTopicByUsername(currentLoggedInUsername());
                model.addAttribute("lesson_top", teacherLessonTopicList);
            } else {
                Lessons lessonById = lessonService.findById(lessonTopics.getId());
                model.addAttribute("lesson_top", lessonById.getLessonTopics());
            }
            model.addAttribute("error", "Failed to create lesson");
            return "admin-page/add-lesson";
        }

        if (mapList.get("topicId").equals("lsTopNotSelected")) {
            model.addAttribute("lessons", teacherLessonList);

            if (tempId == 0) {
                List<LessonTopics> teacherLessonTopicList = lessonTopicService.findAllTeacherLessonTopicByUsername(currentLoggedInUsername());
                model.addAttribute("lesson_top", teacherLessonTopicList);
            } else {
                Lessons lessonById = lessonService.findById(lessonTopics.getId());
                model.addAttribute("lesson_top", lessonById.getLessonTopics());
            }
            model.addAttribute("error", "Please select an option from lesson topic list");
            return "admin-page/add-lesson";
        }

        LessonTopics lsTop = lessonTopicService.findById(Integer.parseInt(mapList.get("topicId")));
        lessons.setLessonTopics(lsTop);
        lessonService.save(lessons);
        redirectAtt.addFlashAttribute("message", "Lesson saved successfully");
        if (tempId != 0) {
            redirectAtt.addFlashAttribute("message", "Lesson update successfully");
            return "redirect:/editCourse";
        }
        return "redirect:/addLesson";
    }

    @GetMapping("deleteLesson/{id}")
    public String deleteLesson(@PathVariable(value = "id") int id, RedirectAttributes redirectAtt) {
        if(checkTeacherLessonsIdByTeacherUsername(id)) {
            redirectAtt.addFlashAttribute("message", "Lesson deleted successfully");
            this.lessonService.deleteLessonById(id);
            return "redirect:/addLesson";
        }
        return "redirect:/error";
    }

    @GetMapping("/addCategory")
    public String addCategory(Model model) {
        List<Category> categoriesList = categoryService.findAllCategories();
        model.addAttribute("category", categoriesList);
        model.addAttribute("newCategory", new Category());
        String role = userService.findRoleByUsername(currentLoggedInUsername());
        model.addAttribute("role", role);
        return "admin-page/add-category";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("newCategory") Category category, BindingResult resultCat, Model model, RedirectAttributes redirectAttributes) {
        courseValidator.validateCategory(category, resultCat);
        if (resultCat.hasErrors()) {
            List<Category> categoriesList = categoryService.findAllCategories();
            model.addAttribute("category", categoriesList);
            model.addAttribute("errormessage", "Failed to create category");
            return "admin-page/add-category";
        }
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Category saved successfully");
        return "redirect:/addCategory";
    }

    @GetMapping("deleteCategory/{id}")
    public String deleteCategory(@PathVariable(value = "id") int id) {
        String role = userService.findRoleByUsername(currentLoggedInUsername());
        if(role.equals("ROLE_ADMIN")) {
            this.categoryService.deleteCategoryById(id);
            return "redirect:/addCategory";
        }
        return "redirect:/error";
    }

    @GetMapping("/updateCategory/{id}")
    public String updateCategory(@PathVariable(value = "id") int id, Model model) {
        String role = userService.findRoleByUsername(currentLoggedInUsername());
        if(role.equals("ROLE_ADMIN")) {
            Category category = categoryService.findById(id);
            model.addAttribute("newCategory", category);
            List<Category> categoriesList = categoryService.findAllCategories();
            model.addAttribute("category", categoriesList);
            return "admin-page/add-category";
        }
        return "redirect:/error";
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
        List<LessonTopics> lessonTopicsList = deleteNullValuesOfLessonTopics(lessonTopicService.findAllLessonTopicByCourseTitle(title));
        for(LessonTopics lessonTopic : lessonTopicsList) {
          Collections.sort(lessonTopic.getLessonsList());
        }
        model.addAttribute("lesTopList", lessonTopicsList);
        List<CourseReviews> courseReviewsList = courseReviewService.findAllByTitle(title);
        model.addAttribute("reviewList", courseReviewsList);

        courseReviewCountRatingByTitle(title, model);
        courseReviewRatingByTitle(title, model);
        lessonsSumByCourseTitle(title, model);
        lessonsCountByCourseTitle(title, model);
        return "course-detail";
    }
    @GetMapping("/buyCourse") // /coursesDetails
    public String addOrders(@RequestParam("courseTitle") String title,
                            @ModelAttribute Orders orders,
                            Model model) {

            model.addAttribute("userBoughtCourse", usersBoughtCourse(title));
            Course course = courseService.findByTitle(title);
            model.addAttribute("coursesTit", course);
            List<LessonTopics> lessonTopicsList = deleteNullValuesOfLessonTopics(lessonTopicService.findAllLessonTopicByCourseTitle(title));
            for(LessonTopics lessonTopic : lessonTopicsList) {
                Collections.sort(lessonTopic.getLessonsList());
            }
            model.addAttribute("lesTopList", lessonTopicsList);
            List<CourseReviews> courseReviewsList = courseReviewService.findAllByTitle(title);
            model.addAttribute("reviewList", courseReviewsList);

            courseReviewCountRatingByTitle(title, model);
            courseReviewRatingByTitle(title, model);
            lessonsSumByCourseTitle(title, model);
            lessonsCountByCourseTitle(title, model);

        Integer userId = userService.findIdByUsername(currentLoggedInUsername());
        Integer courseId = courseService.findIdByCourseTitle(title);

        if(userId != null) {
            orders.setUserId(userId);
            orders.setCertificate_url("url");
            orders.setCourseId(courseId);
            orders.setPrice(course.getPrice());

            orderService.save(orders);
            return "redirect:/coursesDetails?courseTitle="+title;
        }
        model.addAttribute("error", "Please login if you want to buy course");
        return "course-detail";

//        return "redirect:/error";
    }


    @GetMapping("/reviews")
    public String adminPageReviews(@ModelAttribute("orderReviews") String arrangement, Model model) {
        List<CourseReviews> oldestReview = courseReviewService.findAllSortByAnyTime(currentLoggedInUsername());
        List<CourseReviews> latestReview = courseReviewService.findAllSortByLatest(currentLoggedInUsername());

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
    @GetMapping("/setReportTrue/{id}") //Reviews
    public String updateReportTrue(@PathVariable(value = "id") int id) {
        if(checkTeacherCourseReviewsIdByTeacherUsername(id)) {
            courseReviewService.updateCourseReviewToTrue(id);
            return "redirect:/reviews";
        }
        return "redirect:/error";
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
    @GetMapping("/reportedList")  //ReportedList
    public String reportedCommentList(Model model) {
        List<CourseReviews> reportedList = courseReviewService.findReportedReviewsByTeacher();
        model.addAttribute("list", reportedList);
        return "admin-page/reported-comment-list";
    }

    @GetMapping("deleteReportedReview/{id}")//ReportedList
        public String deleteReportedReview(@PathVariable(value = "id") int id, RedirectAttributes redirectAtt) {
            this.courseReviewService.deleteCourseReviewById(id);
            redirectAtt.addFlashAttribute("message", "Review deleted successfully");
            return "redirect:/reportedList";
    }
    @GetMapping("/setReportFalse/{id}")  //ReportedList
    public String updateReportedReviewFalse(@PathVariable(value = "id") int id, RedirectAttributes redirectAtt) {
       courseReviewService.updateCourseReviewToFalse(id);
       redirectAtt.addFlashAttribute("message", "Review restored successfully");
        return "redirect:/reportedList";
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
     *
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
                    for (Lessons lessons : lessonTopics.getLessonsList()) {
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
     *
     * @return logged username or anonymous
     */
    public String currentLoggedInUsername() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
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
            return true;
        }
        return false;
    }

    /**
     *  Teacher can report only his own courses reviews comments to admin
     */
    public Boolean checkTeacherCourseReviewsIdByTeacherUsername(int id) {
        List<Integer> l = courseReviewService.checkReviewsIdByTeacherUsername(currentLoggedInUsername());
        if (l.contains(id)) {
            return true;
        }
        return false;
    }

    /**
     *  Teacher can delete only his own lesson topics
     */
    public Boolean checkTeacherLessonTopicsIdByTeacherUsername(int id) {
        List<Integer> l = lessonTopicService.findLessonTopicIdByTeacherUsername(currentLoggedInUsername());
        if (l.contains(id)) {
            return true;
        }
        return false;
    }

    /**
     *  Teacher can delete only his own lessons
     */
    public Boolean checkTeacherLessonsIdByTeacherUsername(int id) {
        List<Integer> l = lessonService.findLessonsIdByTeacherUsername(currentLoggedInUsername());
        if (l.contains(id)) {
            return true;
        }
        return false;
    }

    /**
     * If LessonTopic don't have lessons inside, hide lessonTopic
     */
    public List<LessonTopics> deleteNullValuesOfLessonTopics(List<LessonTopics> list) {
        Iterator<LessonTopics> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getLessonsList().isEmpty()) {
                it.remove();
            }
        }
        return list;
    }
}
