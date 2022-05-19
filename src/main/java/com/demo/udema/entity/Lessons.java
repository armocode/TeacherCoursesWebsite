package com.demo.udema.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "lessons")
public class Lessons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "lesson_topic_id")
    private LessonTopics lessonTopics;

    @Column(name = "name", unique=true)
    private String name;

    @Column(name = "length")
    private Time length;

    @Column(name = "description")
    private String description;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "is_free")
    private boolean isFree;

    public Lessons()  {}

    public Lessons(int id, LessonTopics lessonTopics, String name, Time length, String description, String videoUrl, boolean isFree) {
        this.id = id;
        this.lessonTopics = lessonTopics;
        this.name = name;
        this.length = length;
        this.description = description;
        this.videoUrl = videoUrl;
        this.isFree = isFree;
    }

    public Lessons(LessonTopics lessonTopics, String name, Time length, String description, String videoUrl, boolean isFree) {
        this.lessonTopics = lessonTopics;
        this.name = name;
        this.length = length;
        this.description = description;
        this.videoUrl = videoUrl;
        this.isFree = isFree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LessonTopics getLessonTopics() {
        return lessonTopics;
    }

    public void setLessonTopics(LessonTopics lessonTopics) {
        this.lessonTopics = lessonTopics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getLength() {
        return length;
    }

    public void setLength(Time length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
