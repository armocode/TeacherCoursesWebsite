package com.demo.udema.entity;

import javax.persistence.*;

@Entity
@Table(name = "lesson_topics")
public class LessonTopics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "lesson_id") //FK
    private Lessons lessons;

public LessonTopics(){}

    public LessonTopics(int id, String name, Lessons lessons) {
        this.id = id;
        this.name = name;
        this.lessons = lessons;
    }

    public LessonTopics(String name, Lessons lessons) {
        this.name = name;
        this.lessons = lessons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lessons getLessons() {
        return lessons;
    }

    public void setLessons(Lessons lessons) {
        this.lessons = lessons;
    }
}
