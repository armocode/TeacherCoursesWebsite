package com.demo.udema.controller;

import com.demo.udema.entity.Course;
import com.demo.udema.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    private CourseService courseService;


    @GetMapping("/search")
    public String searchForCourse(Model model, Course course) {
        model.addAttribute("courseForm", new Course());
        return "html_menu_1/index";
    }
}
