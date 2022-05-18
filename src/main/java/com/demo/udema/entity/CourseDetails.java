package com.demo.udema.entity;

import javax.persistence.*;

@Entity
@Table(name = "course_details")
public class CourseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "course_id") //FK )
    private Course course;

    @Column(name = "description")
    private String description;

    @Column(name = "lessons_total_number")
    private int lessonTotalNumber;

    @Column(name = "lessons_total_length")
    private int lessonTotalLength;

    @ManyToOne()
    @JoinColumn(name = "lesson_topic_id") //FK
    private LessonTopics lessonTopics;

    @ManyToOne()
    @JoinColumn(name = "review_id") //FK
    private CourseReviews courseReviews;


    public CourseDetails() {}

    public CourseDetails(int id, Course course, String description, int lessonTotalNumber, int lessonTotalLength, LessonTopics lessonTopics, CourseReviews courseReviews) {
        this.id = id;
        this.course = course;
        this.description = description;
        this.lessonTotalNumber = lessonTotalNumber;
        this.lessonTotalLength = lessonTotalLength;
        this.lessonTopics = lessonTopics;
        this.courseReviews = courseReviews;
    }

    public CourseDetails(Course course, String description, int lessonTotalNumber, int lessonTotalLength, LessonTopics lessonTopics, CourseReviews courseReviews) {
        this.course = course;
        this.description = description;
        this.lessonTotalNumber = lessonTotalNumber;
        this.lessonTotalLength = lessonTotalLength;
        this.lessonTopics = lessonTopics;
        this.courseReviews = courseReviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLessonTotalNumber() {
        return lessonTotalNumber;
    }

    public void setLessonTotalNumber(int lessonTotalNumber) {
        this.lessonTotalNumber = lessonTotalNumber;
    }

    public int getLessonTotalLength() {
        return lessonTotalLength;
    }

    public void setLessonTotalLength(int lessonTotalLength) {
        this.lessonTotalLength = lessonTotalLength;
    }

    public LessonTopics getLessonTopics() {
        return lessonTopics;
    }

    public void setLessonTopics(LessonTopics lessonTopics) {
        this.lessonTopics = lessonTopics;
    }

    public CourseReviews getCourseReviews() {
        return courseReviews;
    }

    public void setCourseReviews(CourseReviews courseReviews) {
        this.courseReviews = courseReviews;
    }
}
