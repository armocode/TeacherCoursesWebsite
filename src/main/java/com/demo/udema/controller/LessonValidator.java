package com.demo.udema.controller;

import com.demo.udema.entity.LessonTopics;
import com.demo.udema.service.LessonService;
import com.demo.udema.service.LessonTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LessonValidator implements Validator {
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LessonTopicService lessonTopicService;

    Pattern pattern = Pattern.compile("^\\d{0,8}(\\.\\d{1,2})?$");

    @Override
    public boolean supports(Class<?> aClass) {
        return LessonTopics.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        LessonTopics lessonTopics = (LessonTopics) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (lessonTopics.getName().length() < 3 || lessonTopics.getName().length() > 32) {
            errors.rejectValue("name", "Size.course.title");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "listNumber", "NotEmpty");
        Matcher matcher = pattern.matcher(String.valueOf(lessonTopics.getListNumber()));
        if(matcher.find() == false) {
            errors.rejectValue("listNumber", "Matcher.course.price");
        }
    }
}
