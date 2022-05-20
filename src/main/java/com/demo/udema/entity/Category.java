package com.demo.udema.entity;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", unique=true)
    private String title;

    @OneToOne(mappedBy = "category", cascade = CascadeType.ALL) // PK
    private Course course;

    public Category() {}

    public Category(String title, Course course) {
        this.title = title;
        this.course = course;
    }

    public Category(int id, String title, Course course) {
        this.id = id;
        this.title = title;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
