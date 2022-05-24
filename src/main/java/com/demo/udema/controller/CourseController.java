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
        model.addAttribute("courses", courseList);
        return "index";
    }


//    @GetMapping("/searchForCourse")
//   public String searchForCourse(@ModelAttribute("courseForm") Course course, Model model, RedirectAttributes redirectAttributes) {
//        model.addAttribute("courseForm", course);
//        if(course.getTitle().toLowerCase(Locale.ROOT).equals("java")) {
//            redirectAttributes.addFlashAttribute("error", "case 1");
//            return "redirect:/list";
//        } else if (course.getTitle().toLowerCase(Locale.ROOT).equals("")) {
//            redirectAttributes.addFlashAttribute("error", "case 2");
//            return "redirect:/index";
//        }
//       redirectAttributes.addFlashAttribute("error", "case 3");
//       return "redirect:/index";
//   }

}
