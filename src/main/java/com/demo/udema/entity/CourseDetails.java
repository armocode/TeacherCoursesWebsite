package com.demo.udema.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course_details")
public class CourseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "course_id") //FK
    private Course course;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "lessons_total_number", length = 3)
    private int lessonTotalNumber;

    @Column(name = "lessons_total_length")
    private int lessonTotalLength;

    @OneToMany(mappedBy = "courseDetails", cascade = CascadeType.ALL) // PK
    private List<LessonTopics> lessonTopics;

    @OneToMany(mappedBy = "courseDetails", cascade = CascadeType.ALL) // PK
    private List<CourseReviews> courseReviews;


    public CourseDetails() {}

    public CourseDetails(int id, Course course, String description, int lessonTotalNumber, int lessonTotalLength, List<LessonTopics> lessonTopics) {
        this.id = id;
        this.course = course;
        this.description = description;
        this.lessonTotalNumber = lessonTotalNumber;
        this.lessonTotalLength = lessonTotalLength;
        this.lessonTopics = lessonTopics;
    }

    public CourseDetails(Course course, String description, int lessonTotalNumber, int lessonTotalLength, List<LessonTopics> lessonTopics) {
        this.course = course;
        this.description = description;
        this.lessonTotalNumber = lessonTotalNumber;
        this.lessonTotalLength = lessonTotalLength;
        this.lessonTopics = lessonTopics;
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

    public List<LessonTopics> getLessonTopics() {
        return lessonTopics;
    }

    public void setLessonTopics(List<LessonTopics> lessonTopics) {
        this.lessonTopics = lessonTopics;
    }

    public List<CourseReviews> getCourseReviews() {
        return courseReviews;
    }

    public void setCourseReviews(List<CourseReviews> courseReviews) {
        this.courseReviews = courseReviews;
    }

    @Override
    public String toString() {
        return "CourseDetails{" +
                ", description='" + description + '\'' +
                '}';
    }
}
