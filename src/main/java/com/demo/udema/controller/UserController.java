package com.demo.udema.controller;

import com.demo.udema.entity.User;
import com.demo.udema.service.SecurityService;
import com.demo.udema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
//        System.out.println(userForm.getRole());
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if(userForm.getRole().equals("student")) {
            userService.save(userForm);
            securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
            return "redirect:/userPage";
        }
        if(userForm.getRole().equals("teacher")) {
            userService.save(userForm);
            securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
            return "redirect:/teacherPage"; // welcome
        }
//        if(userForm.getRole().equals("admin")) {
//            userService.save(userForm);
//            securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
//            return "redirect:/adminPage";
//        }
        return "registration";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout, User user) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping("/" )
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        return "welcome";
    }

    @GetMapping("/adminPage")
    public String adminPage(Model model) {
        return "admin-page/index";
    }

    @GetMapping("/teacherPage")
    public String teacherPage() {
        return "teacher-page/teacher-profile";
    }

    @GetMapping("/userPage")
    public String userPage() {
        return "user-page/user-profile";
    }

    @GetMapping("/404")
    public String accessDenied(){
        return "404";
    }
}
