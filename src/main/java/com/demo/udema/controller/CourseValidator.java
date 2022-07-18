package com.demo.udema.controller;

import com.demo.udema.entity.*;
import com.demo.udema.service.CategoryService;
import com.demo.udema.service.CourseService;
import com.demo.udema.service.LessonService;
import com.demo.udema.service.LessonTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CourseValidator implements Validator {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LessonTopicService lessonTopicService;
    @Autowired
    private LessonService lessonService;

    Pattern pattern = Pattern.compile("^\\d{0,8}(\\.\\d{1,2})?$");

    @Override
    public boolean supports(Class<?> aClass) {
        return Course.class.equals(aClass);
    }

    //----Course---//
    @Override
    public void validate(Object o, Errors errors) {
        List<Course> coursesList = courseService.findAll();
        Course course = (Course) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
        if (course.getTitle().length() < 6 || course.getTitle().length() > 32) {
            errors.rejectValue("title", "Size.course.title");
        }
        if (course.getId() == 0) {
            if (courseService.findByTitle(course.getTitle()) != null) {
                errors.rejectValue("title", "Duplicate.course.title");
            }
        }
        if (course.getId() != 0) {
            for (Course cL : coursesList) {
                if (cL.getId() != course.getId()) {
                    if (cL.getTitle().equals(course.getTitle())) {
                        errors.rejectValue("title", "Duplicate.course.title");
                    }
                }
            }
        }

//----Course price----//
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty");
        Matcher matcher = pattern.matcher(String.valueOf(course.getPrice()));
        if (matcher.find() == false) {
            errors.rejectValue("price", "Matcher.course.price");
        }

    }

    //----Course details----//
    public void validateCourseDes(Object o, Errors errors) {
        CourseDetails courseDetails = (CourseDetails) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");
        if (courseDetails.getDescription().length() < 6) {
            errors.rejectValue("description", "Size.course.description");
        }
    }

    //----Category----//
    public void validateCategory(Object o, Errors errors) {
        Category category = (Category) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
        if (categoryService.findByTitle(category.getTitle()) != null) {
            errors.rejectValue("title", "Duplicate.category.title");
        }
    }

    //----Lesson topics----//
    public void validateLessonTopic(Object o, Errors errors) {
        List<LessonTopics> lessonTopicsList = lessonTopicService.findAll();
        LessonTopics lessonTopics = (LessonTopics) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (lessonTopics.getName().length() < 3 || lessonTopics.getName().length() > 32) {
            errors.rejectValue("name", "Size.lessonTopic.name");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "listNumber", "NotEmpty");
        Matcher matcher = pattern.matcher(String.valueOf(lessonTopics.getListNumber()));
        if (matcher.find() == false) {
            errors.rejectValue("listNumber", "Matcher.lessonTopic.listNumber");
        }
        if (lessonTopics.getId() == 0) {
            if (lessonTopicService.findByTopicName(lessonTopics.getName()) != null) {
                errors.rejectValue("name", "Duplicate.lessonTopic.name");
            }
            if(lessonTopics.getListNumber() != null){
                if (lessonTopicService.findByListNumber(lessonTopics.getListNumber()) != null) {
                    errors.rejectValue("listNumber", "Duplicate.lessonTopic.listNumber");
                }
            }
        }

        if (lessonTopics.getId() != 0) {
            for (LessonTopics lTop : lessonTopicsList) {
                if (lTop.getId() != lessonTopics.getId()) {
                    if (lTop.getName().equals(lessonTopics.getName())) {
                        errors.rejectValue("name", "Duplicate.lessonTopic.name");
                    }
                    if (lTop.getListNumber().equals(lessonTopics.getListNumber())) {
                        errors.rejectValue("listNumber", "Duplicate.lessonTopic.listNumber");
                    }
                }
            }
        }
    }

    //----Lessons----/
    public void validateLesson(Object o, Errors errors) {
        List<Lessons> lessonList = lessonService.findAll();
        Lessons lessons = (Lessons) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");
        if (lessons.getDescription().length() < 6) {
            errors.rejectValue("description", "Size.lesson.description");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "videoUrl", "NotEmpty");
        if (lessons.getVideoUrl().length() < 3) {
            errors.rejectValue("videoUrl", "Size.lesson.videoUrl");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "length", "NotEmpty");
        Matcher matcher = pattern.matcher(String.valueOf(lessons.getLength()));
        if (matcher.find() == false) {
            errors.rejectValue("length", "Matcher.lesson.length");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (lessons.getName().length() < 3 || lessons.getName().length() > 32) {
            errors.rejectValue("name", "Size.lesson.name");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "listNumber", "NotEmpty");
        Matcher matcher2 = pattern.matcher(String.valueOf(lessons.getListNumber()));
        if (matcher2.find() == false) {
            errors.rejectValue("listNumber", "Matcher.lessonTopic.listNumber");
        }
        if (lessons.getId() == 0) {
            if (lessonService.findByLessonName(lessons.getName()) != null) {
                errors.rejectValue("name", "Duplicate.lesson.name");
            }
            if(lessons.getListNumber() != null){
                if (lessonService.findByListNumber(lessons.getListNumber()) != null) {
                    errors.rejectValue("listNumber", "Duplicate.lesson.listNumber");
                }
            }
        }
        if (lessons.getId() != 0) {
            for (Lessons l : lessonList) {
                if (l.getId() != lessons.getId()) {
                    if (l.getName().equals(lessons.getName())) {
                        errors.rejectValue("name", "Duplicate.lesson.name");
                    }
                    if(lessons.getListNumber() != null) {
                        if (l.getListNumber().equals(lessons.getListNumber())) {
                            errors.rejectValue("listNumber", "Duplicate.lessonTopic.listNumber");
                        }
                    }
                }
            }
        }
    }
}













