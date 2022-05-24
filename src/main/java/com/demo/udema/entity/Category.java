package com.demo.udema.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", unique = true)
    private String title;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL) //// PK
    private List<Course> course;


    public Category() {
    }

    public Category(int id, String title, List<Course> course) {
        this.id = id;
        this.title = title;
        this.course = course;
    }

    public Category(String title, List<Course> course) {
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

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }
}
