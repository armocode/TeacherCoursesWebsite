package com.demo.udema.controller;

import com.demo.udema.entity.Category;
import com.demo.udema.entity.Course;
import com.demo.udema.entity.CourseDetails;
import com.demo.udema.service.CategoryService;
import com.demo.udema.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CourseValidator implements Validator {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CategoryService categoryService;

    Pattern pattern = Pattern.compile("^\\d{0,8}(\\.\\d{1,2})?$");

    @Override
    public boolean supports(Class<?> aClass) {
        return Course.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Course course = (Course) o;

//----title----//
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
        if (course.getTitle().length() < 6 || course.getTitle().length() > 32) {
            errors.rejectValue("title", "Size.course.title");
        }
//        if (courseService.findByTitle(course.getTitle()) != null) {
//            errors.rejectValue("title", "Duplicate.course.title");
//        }

//----price----//
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty");
        Matcher matcher = pattern.matcher(String.valueOf(course.getPrice()));
        if (matcher.find() == false) {
            errors.rejectValue("price", "Matcher.course.price");
        }

    }

    public void validateCourseDes(Object o, Errors errors) {
        CourseDetails courseDetails = (CourseDetails) o;

//----description----//
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");
        if (courseDetails.getDescription().length() < 6) {
            errors.rejectValue("description", "Size.course.description");
        }
    }

    public void validateCategory(Object o, Errors errors) {
        Category category = (Category) o;

//----category----//
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
        if (categoryService.findByTitle(category.getTitle()) != null) {
            errors.rejectValue("title", "Duplicate.category.title");
        }
    }
}












