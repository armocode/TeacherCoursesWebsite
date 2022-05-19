package com.demo.udema.entity;

import javax.persistence.*;

@Entity
@Table(name = "lesson_topics")
public class LessonTopics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "course_details_id") //FK
    private CourseDetails courseDetails;

    @Column(name = "name", unique=true)
    private String name;

    @Column(name = "list_number", unique=true)
    private int listNumber;

public LessonTopics(){}

    public LessonTopics(int id, CourseDetails courseDetails, String name, int listNumber) {
        this.id = id;
        this.courseDetails = courseDetails;
        this.name = name;
        this.listNumber = listNumber;
    }

    public LessonTopics(CourseDetails courseDetails, String name, int listNumber) {
        this.courseDetails = courseDetails;
        this.name = name;
        this.listNumber = listNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CourseDetails getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(CourseDetails courseDetails) {
        this.courseDetails = courseDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getListNumber() {
        return listNumber;
    }

    public void setListNumber(int listNumber) {
        this.listNumber = listNumber;
    }
}
