package com.demo.udema.controller;

import com.demo.udema.entity.Course;
import com.demo.udema.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
public class CourseController {

   private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/list")
    public String coursesList(Model model) {
        List<Course> courseList = courseService.findAll();
//        List<CourseReviews> reviewsList = courseService.findAllByOrderByTitleAsc();
        model.addAttribute("courses", courseList);
//        model.addAttribute("coursesReview", reviewsList);
        return "index";
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

    @GetMapping("/coursesList")
    public String coursesList() {
        return "courses-list";
    }
}
