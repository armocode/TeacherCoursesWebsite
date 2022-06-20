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
    @JoinColumn(name = "lesson_topic_id") //FK
    private LessonTopics lessonTopics;

    @Column(name = "name", unique=true, length = 512)
    private String name;

    @Column(name = "length")
    private Integer length;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "video_url", length = 512)
    private String videoUrl;

    @Column(name = "is_free")
    private boolean isFree;

    @Column(name = "list_number", unique=true, length = 3)
    private Integer listNumber;

    public Lessons()  {}
    public Lessons(int id, LessonTopics lessonTopics, String name, Integer length, String description, String videoUrl, boolean isFree, Integer listNumber) {
        this.id = id;
        this.lessonTopics = lessonTopics;
        this.name = name;
        this.length = length;
        this.description = description;
        this.videoUrl = videoUrl;
        this.isFree = isFree;
        this.listNumber = listNumber;
    }
    public Lessons(LessonTopics lessonTopics, String name, Integer length, String description, String videoUrl, boolean isFree, Integer listNumber) {
        this.lessonTopics = lessonTopics;
        this.name = name;
        this.length = length;
        this.description = description;
        this.videoUrl = videoUrl;
        this.isFree = isFree;
        this.listNumber = listNumber;
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

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
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

    public Integer getListNumber() {
        return listNumber;
    }

    public void setListNumber(Integer listNumber) {
        this.listNumber = listNumber;
    }
}
