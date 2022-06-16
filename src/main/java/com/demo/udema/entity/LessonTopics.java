package com.demo.udema.entity;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

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

//    @NotEmpty(message = "Field can't be empty!")
//    @Size(min = 5, message = "test")
    @Column(name = "name", unique=true, length = 512)
    private String name;

    @Column(name = "list_number", unique=true, length = 3)
    private int listNumber;

    @OneToMany(mappedBy = "lessonTopics", cascade = CascadeType.ALL) //PK
    private List<Lessons> lessonsList;


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

    public List<Lessons> getLessonsList() {
        return lessonsList;
    }

    public void setLessonsList(List<Lessons> lessonsList) {
        this.lessonsList = lessonsList;
    }

    @Override
    public String toString() {
        return "LessonTopics{" +
                "id=" + id +
                ", courseDetails=" + courseDetails +
                ", name='" + name + '\'' +
                ", listNumber=" + listNumber +
                ", lessonsList=" + lessonsList +
                '}';
    }
}
